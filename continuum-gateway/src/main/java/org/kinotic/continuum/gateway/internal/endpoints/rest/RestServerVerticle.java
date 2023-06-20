/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.continuum.gateway.internal.endpoints.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.kinotic.continuum.core.api.event.Event;
import org.kinotic.continuum.core.api.event.EventBusService;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.continuum.core.api.security.Participant;
import org.kinotic.continuum.core.api.security.SecurityService;
import org.kinotic.continuum.gateway.api.config.ContinuumGatewayProperties;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.UUID;

/**
 * Vertx Verticle to convert REST requests to continuum requests..
 *
 *
 * Created by navid on 12/18/19
 */
@Component
public class RestServerVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(RestServerVerticle.class);

    private final HashMap<String, RoutingContext> responseCorrelationMap = new HashMap<>();

    private final ContinuumGatewayProperties gatewayProperties;
    private final EventBusService eventService;
    private final SecurityService securityService;
    private final ObjectMapper objectMapper;

    private Scheduler scheduler;

    private Disposable disposable;
    private HttpServer httpServer;

    public RestServerVerticle(ContinuumGatewayProperties gatewayProperties,
                              EventBusService eventService,
                              @Autowired(required = false) SecurityService securityService,
                              ObjectMapper objectMapper) {
        this.gatewayProperties = gatewayProperties;
        this.eventService = eventService;
        this.securityService = securityService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        final String replyDestination = EventConstants.SERVICE_DESTINATION_SCHEME + "://" + UUID.randomUUID() + "@continuum.java.rest.EventBus";
        final String restPath = gatewayProperties.getRest().getRestPath();

        // Create scheduler for reactor objects
        Context context = vertx.getOrCreateContext();
        scheduler = Schedulers.fromExecutor(command -> context.runOnContext(v -> command.run()));

        httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route("/*").handler(StaticHandler.create());

        Route restRoute = router.route(HttpMethod.POST, restPath+"/*");

        if(securityService !=null){
            restRoute.handler(new AuthenticationHandler(securityService, vertx));
        }

        // will ensure body is available
        restRoute.handler(BodyHandler.create()
                 .setBodyLimit(gatewayProperties.getRest().getBodyLimitSize()));

        // will dispatch to event service
        restRoute.handler(routingContext -> {
            String claimId = UUID.randomUUID().toString();
            try {
                // create event for incoming request
                Event<byte[]> requestEvent = new RoutingContextEventAdapter(restPath, routingContext);
                // set reply header
                requestEvent.metadata().put(EventConstants.REPLY_TO_HEADER, replyDestination+"/replyHandler");

                // set claim ticket
                requestEvent.metadata().put(EventConstants.CORRELATION_ID_HEADER, claimId);
                responseCorrelationMap.put(claimId, routingContext);

                Participant participant = routingContext.get(EventConstants.SENDER_HEADER);
                if(participant != null){
                    requestEvent.metadata().put(EventConstants.SENDER_HEADER, objectMapper.writeValueAsString(participant));
                }

                // now send event to invoke remote service
                eventService.sendWithAck(requestEvent)
                            .publishOn(scheduler)
                            .subscribe(null,
                                       throwable -> {
                                           routingContext.response().setStatusCode(500);
                                           routingContext.response().setStatusMessage(throwable.getMessage());
                                           routingContext.response().end();
                                       });

            } catch (Exception e) {
                // problem processing request
                responseCorrelationMap.remove(claimId);
                routingContext.response().setStatusCode(500);
                routingContext.response().setStatusMessage(e.getMessage());
                routingContext.response().end();
            }
        });

        // Setup listener for RPC invocation responses
        Mono<Flux<Event<byte[]>>> eventMono = eventService.listenWithAck(replyDestination)
                                                          .publishOn(scheduler);
        eventMono.subscribe(eventFlux -> {
            disposable =
                eventFlux.publishOn(scheduler)
                         .subscribe(this::processResponseEvent, // will be called on response event
                                    // received error
                                    throwable -> log.error("Event listener error", throwable),
                                    // listener completed
                                    () -> {
                                        log.error("Should not happen! Event listener stopped for some reason!!");
                                    });

            // now that response listener is active start http server to handle incoming requests
            httpServer.requestHandler(router)
                      .listen(gatewayProperties.getRest().getPort(), ar -> {
                          if (ar.succeeded()) {
                              log.info("REST Server Listening on port "+ ar.result().actualPort());
                              startPromise.complete();
                          } else {
                              log.error("Error starting REST Server", ar.cause());
                              startPromise.fail(ar.cause());
                          }
                      });
        },
        startPromise::fail);
    }


    private void processResponseEvent(Event<byte[]> event){
        String id = event.metadata().get(EventConstants.CORRELATION_ID_HEADER);
        if(id != null){
            RoutingContext context = responseCorrelationMap.get(id);
            if(context != null){
                String errorHeader = event.metadata().get(EventConstants.ERROR_HEADER);

                if(errorHeader == null) {
                    context.response().setStatusCode(200);
                }else{
                    context.response().setStatusCode(500);
                }

                if(event.data() != null) {
                    context.response().putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(event.data().length));
                    if(event.metadata().contains(EventConstants.CONTENT_TYPE_HEADER)){
                        context.response().putHeader(HttpHeaders.CONTENT_TYPE, event.metadata().get(EventConstants.CONTENT_TYPE_HEADER));
                    }
                    context.response().write(Buffer.buffer(event.data()));
                }else if(errorHeader != null){
                    context.response().putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(errorHeader.length()));
                    context.response().write(Buffer.buffer(errorHeader));
                }

                context.response().end();
            }else{
                log.error("Received RPC response for "+EventConstants.CORRELATION_ID_HEADER+": "+id + " but no context is set");
            }
        }else{
            log.error("Received RPC response that does not contain a "+EventConstants.CORRELATION_ID_HEADER+" header");
        }
    }

    @Override
    public void stop() throws Exception {
        httpServer.close();
        disposable.dispose();
    }
}

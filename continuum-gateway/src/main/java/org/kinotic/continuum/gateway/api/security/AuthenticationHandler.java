package org.kinotic.continuum.gateway.api.security;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.continuum.core.api.security.SecurityService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪on 6/19/23.
 */
public class AuthenticationHandler implements Handler<RoutingContext> {

    private final SecurityService securityService;
    private final Vertx vertx;

    public AuthenticationHandler(SecurityService securityService, Vertx vertx) {
        this.securityService = securityService;
        this.vertx = vertx;
    }

    @Override
    public void handle(RoutingContext ctx) {

        if (handlePreflight(ctx)) {
            return;
        }
        // must pause receiving of content during auth, otherwise the body handler must be before auth which seems baaad!
        ctx.request().pause();

        Map<String, String> authInfo = new HashMap<>(ctx.request().headers().size());
        for(Map.Entry<String, String> entry : ctx.request().headers()){
            authInfo.put(entry.getKey(), entry.getValue());
        }
        VertxCompletableFuture.from(vertx, securityService.authenticate(authInfo))
                .whenComplete((participant, throwable) -> {
                    if(throwable != null){
                        ctx.request().resume();
                        ctx.fail(401, throwable);
                    }else{
                        ctx.put(EventConstants.SENDER_HEADER, participant);
                        ctx.request().resume();
                        ctx.next();
                    }
                });
    }

    private boolean handlePreflight(RoutingContext ctx) {
        final HttpServerRequest request = ctx.request();
        // See: https://www.w3.org/TR/cors/#cross-origin-request-with-preflight-0
        // Preflight requests should not be subject to security due to the reason UAs will remove the Authorization header
        if (request.method() == HttpMethod.OPTIONS) {
            // check if there is a access control request header
            final String accessControlRequestHeader = ctx.request().getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
            if (accessControlRequestHeader != null) {
                // lookup for the Authorization header
                for (String ctrlReq : accessControlRequestHeader.split(",")) {
                    if (ctrlReq.equalsIgnoreCase("Authorization")) {
                        // this request has auth in access control, so we can allow preflighs without authentication
                        ctx.next();
                        return true;
                    }
                }
            }
        }

        return false;
    }

}

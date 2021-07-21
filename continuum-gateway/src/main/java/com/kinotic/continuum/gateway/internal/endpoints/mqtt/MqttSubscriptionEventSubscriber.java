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

package com.kinotic.continuum.gateway.internal.endpoints.mqtt;

import com.kinotic.continuum.core.api.event.Event;
import com.kinotic.continuum.gateway.internal.endpoints.stomp.GatewayUtils;
import com.kinotic.continuum.gateway.internal.endpoints.stomp.StompSubscriptionEventSubscriber;
import io.vertx.ext.stomp.StompServerConnection;
import io.vertx.ext.stomp.frame.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.BaseSubscriber;

/**
 *
 * Created by Navid Mitchell on 11/3/20
 */
public class MqttSubscriptionEventSubscriber extends BaseSubscriber<Event<byte[]>> {

    private static final Logger log = LoggerFactory.getLogger(StompSubscriptionEventSubscriber.class);

    private final String destination;
    private final String subscriptionId;
    private final StompServerConnection connection;

    public MqttSubscriptionEventSubscriber(String destination,
                                            String subscriptionId,
                                            StompServerConnection connection) {
        this.destination = destination;
        this.subscriptionId = subscriptionId;
        this.connection = connection;
    }

    @Override
    protected void hookOnNext(Event<byte[]> event) {
        try {
            Frame frame = GatewayUtils.eventToStompFrame(event);
            // Set Subscription ID header
            frame.getHeaders().put(Frame.SUBSCRIPTION, subscriptionId);

            if(log.isTraceEnabled()) {
                log.trace("Sending Frame\n" + frame.toString());
            }

            connection.write(frame);

        } catch (Exception e) {
            log.error("Unexpected Error in Handler " + e.getMessage(), e);
            log.error("Closing connection");
            dispose();
            connection.close();
        }
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        log.error("Error on event bus subscription for destination "+ destination, throwable);
    }

}

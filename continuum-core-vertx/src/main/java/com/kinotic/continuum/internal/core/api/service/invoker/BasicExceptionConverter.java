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

package com.kinotic.continuum.internal.core.api.service.invoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinotic.continuum.api.config.ContinuumProperties;
import com.kinotic.continuum.core.api.event.Event;
import com.kinotic.continuum.core.api.event.EventConstants;
import com.kinotic.continuum.core.api.service.ServiceExceptionWrapper;
import com.kinotic.continuum.internal.util.EventUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.EncodingException;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Navid Mitchell on 2019-04-08.
 */
@Component
@Order
public class BasicExceptionConverter implements ExceptionConverter {

    private static final Logger log = LoggerFactory.getLogger(BasicExceptionConverter.class);

    private final ContinuumProperties properties;
    private final ObjectMapper objectMapper;

    public BasicExceptionConverter(ContinuumProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(Event<byte[]> incomingEvent) {
        return true;
    }

    @Override
    public Event<byte[]> convert(Event<byte[]> incomingEvent, Throwable throwable) {
        Map<String, String> headers = new HashMap<>(2);

        headers.put(EventConstants.ERROR_HEADER, throwable.getMessage());
        headers.put(EventConstants.CONTENT_TYPE_HEADER, MimeTypeUtils.APPLICATION_JSON_VALUE);

        return EventUtils.createReplyEvent(incomingEvent, headers, () -> {
            ServiceExceptionWrapper wrapper = new ServiceExceptionWrapper(throwable.getMessage(), throwable.getClass().getName());

            if(properties.isDebug()) {
                wrapper.setStackTrace(throwable.getStackTrace());
            }
            try {
                return objectMapper.writeValueAsBytes(wrapper);
            } catch (JsonProcessingException e) {
                throw new EncodingException("JSON encoding error: " + e.getOriginalMessage(), e);
            }
        });
    }

}

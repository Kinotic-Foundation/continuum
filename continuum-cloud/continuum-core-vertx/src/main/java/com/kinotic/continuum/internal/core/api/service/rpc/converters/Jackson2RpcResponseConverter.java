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

package com.kinotic.continuum.internal.core.api.service.rpc.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinotic.continuum.core.api.event.Event;
import com.kinotic.continuum.internal.core.api.service.json.AbstractJackson2Support;
import com.kinotic.continuum.internal.core.api.service.rpc.RpcResponseConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * Created by navid on 2019-04-23.
 */
@Component
public class Jackson2RpcResponseConverter extends AbstractJackson2Support implements RpcResponseConverter {

    public Jackson2RpcResponseConverter(ObjectMapper objectMapper,
                                        ReactiveAdapterRegistry reactiveAdapterRegistry) {
        super(objectMapper, reactiveAdapterRegistry);
    }

    @Override
    public boolean supports(Event<byte[]> responseEvent, MethodParameter methodParameter) {
        return containsJsonContent(responseEvent.metadata());
    }

    @Override
    public Object convert(Event<byte[]> responseEvent, MethodParameter methodParameter) {
        Object ret = null;
        if(responseEvent.data()!= null
                && responseEvent.data().length > 0){

            Assert.notNull(methodParameter, "The return type is null but event data was found");

            Object[] temp = createJavaObjectsFromJsonEvent(responseEvent, new MethodParameter[]{methodParameter}, false);

            // We know no more than a single response can be returned because of check above
            // however we want to verify we got at least one
            if(temp.length == 1){

                ret = temp[0];

            }else if(temp.length <= 0){
                throw new IllegalStateException("Event data was present but no values could be converted");
            }
        }
        return ret;
    }



}

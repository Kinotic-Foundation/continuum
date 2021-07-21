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

import com.kinotic.continuum.core.api.event.Event;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by Navid Mitchell on 2019-03-30.
 */
public class ExceptionConverterComposite implements ExceptionConverter {

    private final List<ExceptionConverter> converters = new LinkedList<>();

    public ExceptionConverterComposite addConverter(ExceptionConverter converter){
        converters.add(converter);
        return this;
    }

    public ExceptionConverterComposite addConverters(ExceptionConverter... converters){
        if (converters != null) {
            Collections.addAll(this.converters, converters);
        }
        return this;
    }

    public ExceptionConverterComposite addConverters(List<? extends ExceptionConverter> converters){
        this.converters.addAll(converters);
        return this;
    }
    
    @Override
    public boolean supports(Event<byte[]> incomingEvent) {
        return selectConverter(incomingEvent) != null;
    }

    @Override
    public Event<byte[]> convert(Event<byte[]> incomingEvent, Throwable throwable) {
        ExceptionConverter converter = selectConverter(incomingEvent);
        Assert.notNull(converter,"No ExceptionConverter can be found. Should call supports() first.");
        return converter.convert(incomingEvent, throwable);
    }

    private ExceptionConverter selectConverter(Event<byte[]> incomingMessage){
        ExceptionConverter ret = null;
        for(ExceptionConverter converter : converters){
            if(converter.supports(incomingMessage)){
                ret = converter;
                break;
            }
        }
        return ret;
    }

}

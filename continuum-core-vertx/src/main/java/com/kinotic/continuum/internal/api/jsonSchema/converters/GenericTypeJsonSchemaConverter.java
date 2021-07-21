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

package com.kinotic.continuum.internal.api.jsonSchema.converters;

import org.springframework.core.ResolvableType;

/**
 * Provides a {@link JsonSchemaConverter} that supports arbitrary {@link ResolvableType} types.
 *
 * This can be used for things like Converting all {@link Number} classes.
 *
 *
 * Created by navid on 2019-06-14.
 */
public interface GenericTypeJsonSchemaConverter extends JsonSchemaConverter{

    /**
     * Checks if the given {@link ResolvableType} is supported by this converter
     *
     * @param resolvableType to check if supported
     * @return true if this converter can convert the class false if not
     */
    boolean supports(ResolvableType resolvableType);

}

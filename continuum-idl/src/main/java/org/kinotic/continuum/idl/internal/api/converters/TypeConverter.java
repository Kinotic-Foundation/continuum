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

package org.kinotic.continuum.idl.internal.api.converters;

import org.kinotic.continuum.idl.api.TypeDefinition;
import org.springframework.core.ResolvableType;

/**
 * Provides support for converting individual {@link ResolvableType}'s into the appropriate {@link TypeDefinition}
 *
 *
 * Created by navid on 2019-06-13.
 */
public interface TypeConverter {

    /**
     * Converts the given {@link ResolvableType} to the correct {@link TypeDefinition}
     *
     * @param resolvableType to convert
     * @param conversionContext for this conversion process
     * @return the newly created {@link TypeDefinition} for the class
     */
    TypeDefinition convert(ResolvableType resolvableType,
                           ConversionContext conversionContext);

}

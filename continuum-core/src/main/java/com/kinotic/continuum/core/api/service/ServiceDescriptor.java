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

package com.kinotic.continuum.core.api.service;

import java.util.Collection;

/**
 * Describes services that can be registered with the Continuum
 * Created by Navíd Mitchell 🤬 on 7/18/21.
 */
public interface ServiceDescriptor {

    /**
     * This identifies the {@link ServiceDescriptor}
     * @return the {@link ServiceIdentifier} for this
     */
    ServiceIdentifier serviceIdentifier();

    /**
     * All of the {@link ServiceFunction}'s for this {@link ServiceDescriptor}
     * @return the list of functions
     */
    Collection<ServiceFunction> functions();

    static ServiceDescriptor create(ServiceIdentifier serviceIdentifier){
        return new DefaultServiceDescriptor(serviceIdentifier);
    }

    static ServiceDescriptor create(ServiceIdentifier serviceIdentifier, Collection<ServiceFunction> serviceFunctions){
        return new DefaultServiceDescriptor(serviceIdentifier, serviceFunctions);
    }

}

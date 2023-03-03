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

package org.kinotic.continuum.gateway.api.security;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.annotations.Version;
import org.kinotic.continuum.core.api.event.StreamData;
import org.kinotic.continuum.core.api.security.SessionMetadata;
import reactor.core.publisher.Flux;

/**
 *
 * Created by Navid Mitchell on 6/3/20
 */
@Publish
@Version("0.1.0")
public interface SessionInformationService {

    Flux<Long> countActiveSessionsContinuous();

    Flux<StreamData<String, SessionMetadata>> listActiveSessionsContinuous();

}

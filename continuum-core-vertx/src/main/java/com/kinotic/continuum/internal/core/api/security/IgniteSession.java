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

package com.kinotic.continuum.internal.core.api.security;

import com.kinotic.continuum.core.api.security.Participant;
import org.apache.ignite.IgniteCache;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by Navid Mitchell on 6/2/20
 */
public class IgniteSession extends AbstractSession {

    private final IgniteCache<String, DefaultSessionMetadata> sessionCache;

    public IgniteSession(Participant participant,
                         String sessionId,
                         PathContainer.Options parseOptions,
                         List<PathPattern> sendPathPatterns,
                         List<PathPattern> subscribePathPatterns,
                         IgniteCache<String, DefaultSessionMetadata> sessionCache) {
        super(participant, sessionId, parseOptions, sendPathPatterns, subscribePathPatterns);
        this.sessionCache = sessionCache;
    }

    @Override
    public void touch() {
        lastUsedDate = new Date();
        sessionCache.invoke(sessionId(), (entry, args) -> {
            DefaultSessionMetadata meta = entry.getValue();
            meta.lastUsedDate(lastUsedDate);
            entry.setValue(meta);
            return null;
        });

    }

}

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

package com.kinotic.continuum.grind.internal.api;

import com.kinotic.continuum.grind.api.Task;
import org.springframework.context.support.GenericApplicationContext;

/**
 * A {@link Task} that just passes the provided value straight through without any autowiring or invocation
 *
 * Created by Navid Mitchell on 3/19/20
 */
public class ValueTask<R> extends AbstractTask<R> {

    private final R value;

    public ValueTask(R value) {
        this.value = value;
    }

    public ValueTask(String description, R value) {
        super(description);
        this.value = value;
    }

    @Override
    public R execute(GenericApplicationContext applicationContext) {
        return value;
    }
}

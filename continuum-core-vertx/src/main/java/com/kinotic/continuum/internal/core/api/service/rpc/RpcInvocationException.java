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

package com.kinotic.continuum.internal.core.api.service.rpc;

/**
 * Ths exception is thrown When the original error message cannot be rethrown during a rpc service invocation
 *
 * Created by navid on 11/7/19
 */
public class RpcInvocationException extends RuntimeException {

    private String originalClassName;

    private StackTraceElement[] originalStackTrace;

    public RpcInvocationException(String message) {
        super(message);
    }

    public String getOriginalClassName() {
        return originalClassName;
    }

    public RpcInvocationException setOriginalClassName(String originalClassName) {
        this.originalClassName = originalClassName;
        return this;
    }

    public StackTraceElement[] getOriginalStackTrace() {
        return originalStackTrace;
    }

    public RpcInvocationException setOriginalStackTrace(StackTraceElement[] originalStackTrace) {
        this.originalStackTrace = originalStackTrace;
        return this;
    }
}

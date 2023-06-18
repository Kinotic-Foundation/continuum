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

package org.kinotic.continuum.core.api.security;

import java.util.Map;

/**
 *
 * Created by navid on 2/3/20
 */
public class MetadataConstants {
    public static final String TYPE_KEY = "type";
    public static final Map.Entry<String, String> DEVICE_TYPE = Map.entry(TYPE_KEY, "device");
    public static final Map.Entry<String, String> USER_TYPE = Map.entry(TYPE_KEY, "user");
    public static final Map.Entry<String, String> NODE_TYPE = Map.entry(TYPE_KEY, "node");

}

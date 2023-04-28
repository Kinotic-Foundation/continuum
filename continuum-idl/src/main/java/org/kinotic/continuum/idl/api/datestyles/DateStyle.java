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

package org.kinotic.continuum.idl.api.datestyles;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;

/**
 * The date style to use when serializing and deserializing dates
 * Created by nic on 2019-12-10.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "style")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UnixDateStyle.class, name = "unix"),
        @JsonSubTypes.Type(value = MillsDateStyle.class, name = "epoch"),
        @JsonSubTypes.Type(value = StringDateStyle.class, name = "string")
})
@EqualsAndHashCode
public abstract class DateStyle {

}

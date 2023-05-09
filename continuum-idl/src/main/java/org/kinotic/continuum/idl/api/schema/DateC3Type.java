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

package org.kinotic.continuum.idl.api.schema;

import lombok.*;
import lombok.experimental.Accessors;
import org.kinotic.continuum.idl.api.schema.datestyles.DateStyle;

/**
 * Represents a date type
 * Created by nic on 2019-12-10.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DateC3Type extends C3Type {

    /**
     * The date format to use when serializing and deserializing dates.
     * It is of type {@link DateStyle} since in json this will be expressed as { "type": "date", "format": { "style": "unix" } }
     * <p>
     * TODO: add not null constraint
     */
    private DateStyle format = null;

}

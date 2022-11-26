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

package org.kinotic.continuum.internal.core.api.crud;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.kinotic.continuum.core.api.crud.SearchComparator;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Created by Navíd Mitchell 🤪 on 7/30/21.
 */
@JsonComponent
public class SearchComparatorSerializer extends JsonSerializer<SearchComparator> {

    @Override
    public void serialize(SearchComparator value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeString(value.getStringValue());
    }
}

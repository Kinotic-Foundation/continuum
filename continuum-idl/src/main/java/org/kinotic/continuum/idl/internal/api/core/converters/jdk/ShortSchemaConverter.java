package org.kinotic.continuum.idl.internal.api.core.converters.jdk;

import org.kinotic.continuum.idl.api.Schema;
import org.kinotic.continuum.idl.api.ShortSchema;
import org.kinotic.continuum.idl.internal.api.core.converters.ConversionContext;
import org.kinotic.continuum.idl.internal.api.core.converters.SpecificTypeSchemaConverter;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 4/13/23.
 */
@Component
public class ShortSchemaConverter implements SpecificTypeSchemaConverter {

    private static final Class<?>[] supports = {short.class, Short.class};

    @Override
    public Class<?>[] supports() {
        return supports;
    }

    @Override
    public Schema convert(ResolvableType resolvableType,
                          ConversionContext conversionContext) {
        return new ShortSchema();
    }

}

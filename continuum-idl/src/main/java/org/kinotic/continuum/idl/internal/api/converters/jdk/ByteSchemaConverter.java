package org.kinotic.continuum.idl.internal.api.converters.jdk;

import org.kinotic.continuum.idl.api.ByteSchema;
import org.kinotic.continuum.idl.api.Schema;
import org.kinotic.continuum.idl.internal.api.converters.ConversionContext;
import org.kinotic.continuum.idl.internal.api.converters.SpecificTypeSchemaConverter;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 4/13/23.
 */
@Component
public class ByteSchemaConverter implements SpecificTypeSchemaConverter {

    private static final Class<?>[] supports = {byte.class, Byte.class};

    @Override
    public Class<?>[] supports() {
        return supports;
    }

    @Override
    public Schema convert(ResolvableType resolvableType,
                          ConversionContext conversionContext) {
        return new ByteSchema();
    }

}


package org.kinotic.continuum.idl.internal.api.converters.jdk;

import org.kinotic.continuum.idl.api.FloatTypeDefinition;
import org.kinotic.continuum.idl.api.TypeDefinition;
import org.kinotic.continuum.idl.internal.api.converters.ConversionContext;
import org.kinotic.continuum.idl.internal.api.converters.SpecificTypeConverter;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 4/13/23.
 */
@Component
public class FloatTypeConverter implements SpecificTypeConverter {

    private static final Class<?>[] supports = {float.class, Float.class};

    @Override
    public Class<?>[] supports() {
        return supports;
    }

    @Override
    public TypeDefinition convert(ResolvableType resolvableType,
                                  ConversionContext conversionContext) {
        return new FloatTypeDefinition();
    }

}


package org.kinotic.continuum.idl.internal.api.jdk;

import org.kinotic.continuum.idl.api.DoubleC3Type;
import org.kinotic.continuum.idl.api.C3Type;
import org.kinotic.continuum.idl.internal.api.ConversionContext;
import org.kinotic.continuum.idl.internal.api.SpecificTypeConverter;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 4/13/23.
 */
@Component
public class DoubleTypeConverter implements SpecificTypeConverter {

    private static final Class<?>[] supports = {double.class, Double.class};

    @Override
    public Class<?>[] supports() {
        return supports;
    }

    @Override
    public C3Type convert(ResolvableType resolvableType,
                          ConversionContext conversionContext) {
        return new DoubleC3Type();
    }

}

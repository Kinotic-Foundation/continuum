package org.kinotic.continuum.idl.internal.converter;

import org.kinotic.continuum.idl.api.C3Type;
import org.kinotic.continuum.idl.converter.C3ConversionContext;
import org.kinotic.continuum.idl.converter.IdlConverter;
import org.kinotic.continuum.idl.converter.IdlConverterStrategy;

/**
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
public class DefaultIdlConverter<T, S> implements IdlConverter<T, S> {

    private final C3ConversionContext<T, S> conversionContext;

    public DefaultIdlConverter(IdlConverterStrategy<T, S> strategy) {
        this.conversionContext = new DefaultC3ConversionContext<>(strategy);
    }

    @Override
    public T convert(C3Type type) {
        return conversionContext.convert(type);
    }

    @Override
    public C3ConversionContext<T, S> getConversionContext() {
        return conversionContext;
    }
}

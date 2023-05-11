package org.kinotic.continuum.idl.internal.api.converter;

import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for {@link IdlConverterStrategy} implementations.
 * This class handles the selection of the correct {@link C3TypeConverter} for a given {@link C3Type}
 * It does this by first searching the {@link SpecificC3TypeConverter}'s then the {@link GenericC3TypeConverter}'s
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public abstract class AbstractIdlConverterStrategy<R, S> implements IdlConverterStrategy<R, S>{

    /**
     * The {@link SpecificC3TypeConverter}s that this strategy uses
     */
    private final Map<String, SpecificC3TypeConverter<R, ?, S>> specificConverters = new LinkedHashMap<>();

    /**
     * The {@link GenericC3TypeConverter}s that this strategy uses
     */
    private final List<GenericC3TypeConverter<R, ?, S>> genericTypeConverters;

    public AbstractIdlConverterStrategy(List<SpecificC3TypeConverter<R, ?, S>> specificTypeConverters,
                                        List<GenericC3TypeConverter<R, ?, S>> genericTypeConverters) {

        this.genericTypeConverters = genericTypeConverters;

        for(SpecificC3TypeConverter<R, ?, S> converter : specificTypeConverters){
            for(Class<? extends C3Type> type: converter.supports()){
                Validate.notNull(type, "SpecificC3TypeConverter classes returned from supports must not be null");
                Validate.isTrue(!specificConverters.containsKey(type.getName()),"SpecificC3TypeConverter already exists for "+type.getName());

                specificConverters.put(type.getName(), converter);
            }
        }
    }

    @Override
    public C3TypeConverter<R, ?, S> converterFor(C3Type c3Type) {
        C3TypeConverter<R, ?, S> ret = null;
        // check specific type then generic converters
        Class<? extends C3Type> clazz = c3Type.getClass();
        if (clazz != null) {
            ret = specificConverters.get(clazz.getName());
        }

        if (ret == null) {
            for (GenericC3TypeConverter<R, ?, S> converter : genericTypeConverters) {
                if (converter.supports(c3Type)) {
                    ret = converter;
                    break;
                }
            }
        }
        return ret;
    }

}

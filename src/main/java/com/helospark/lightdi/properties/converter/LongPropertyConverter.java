package com.helospark.lightdi.properties.converter;

import com.helospark.lightdi.properties.PropertyConverter;

public class LongPropertyConverter implements PropertyConverter<Long> {

    @Override
    public Long convert(String property) {
        return Long.valueOf(property);
    }

}

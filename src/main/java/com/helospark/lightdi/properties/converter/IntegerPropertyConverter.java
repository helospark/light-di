package com.helospark.lightdi.properties.converter;

import com.helospark.lightdi.properties.PropertyConverter;

public class IntegerPropertyConverter implements PropertyConverter<Integer> {

    @Override
    public Integer convert(String property) {
        return Integer.valueOf(property);
    }

}

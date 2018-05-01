package com.helospark.lightdi.properties.converter;

import com.helospark.lightdi.properties.PropertyConverter;

public class StringPropertyConverter implements PropertyConverter<String> {

    @Override
    public String convert(String property) {
        return property;
    }

}

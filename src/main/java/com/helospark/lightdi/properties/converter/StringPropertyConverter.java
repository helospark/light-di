package com.helospark.lightdi.properties.converter;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.properties.PropertyConverter;

@Component
public class StringPropertyConverter implements PropertyConverter<String> {

    @Override
    public String convert(String property) {
        return property;
    }

}

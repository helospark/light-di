package com.helospark.lightdi.properties.converter;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.properties.PropertyConverter;

@Component
public class BooleanPropertyConverter implements PropertyConverter<Boolean> {

    @Override
    public Boolean convert(String property) {
        return Boolean.valueOf(property);
    }

}

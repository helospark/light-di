package com.helospark.lightdi.it.customconverter;

import java.math.BigInteger;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.properties.PropertyConverter;

@Component
public class CustomConverter implements PropertyConverter<BigInteger> {

    @Override
    public BigInteger convert(String property) {
        return new BigInteger(property);
    }

}

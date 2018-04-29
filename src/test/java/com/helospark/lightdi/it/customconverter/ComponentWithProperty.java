package com.helospark.lightdi.it.customconverter;

import java.math.BigInteger;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithProperty {
    @Value("${INT_VALUE}")
    private BigInteger bigInteger;

    public BigInteger getBigInteger() {
        return bigInteger;
    }

}

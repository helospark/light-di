package com.helospark.lightdi.it.propertyoverride;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class BeanWithProperty {
    @Value("${TEST_VALUE}")
    private String value;

    public String getValue() {
        return value;
    }

}

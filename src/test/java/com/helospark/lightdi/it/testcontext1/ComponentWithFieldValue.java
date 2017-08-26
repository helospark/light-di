package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithFieldValue {
    @Value("value=${TEST_VALUE}")
    private String value;

    public String getValue() {
        return value;
    }

}

package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithSetterValue {
    private String value;

    @Value("${TEST_VALUE}=${TEST_VALUE}")
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

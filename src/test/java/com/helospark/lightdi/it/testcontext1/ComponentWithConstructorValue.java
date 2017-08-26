package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.Value;

@Component
@PropertySource("classpath:test_properties.properties")
public class ComponentWithConstructorValue {
    private String value;

    public ComponentWithConstructorValue(@Value("${TEST_VALUE}") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

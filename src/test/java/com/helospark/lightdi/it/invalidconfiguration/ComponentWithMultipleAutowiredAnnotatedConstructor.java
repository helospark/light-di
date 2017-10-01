package com.helospark.lightdi.it.invalidconfiguration;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class ComponentWithMultipleAutowiredAnnotatedConstructor {
    private String value1;
    private String value2;

    @Autowired
    public ComponentWithMultipleAutowiredAnnotatedConstructor(String value1) {
        this.value1 = value1;
    }

    @Autowired
    public ComponentWithMultipleAutowiredAnnotatedConstructor(String value1, String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

}

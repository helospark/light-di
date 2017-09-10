package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.properties.Environment;

@Component
public class EnvironmentInjectTest {
    private String value;

    public EnvironmentInjectTest(Environment environment) {
        value = environment.resolve("${TEST_VALUE}");
    }

    public String getValue() {
        return value;
    }

}

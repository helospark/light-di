package com.helospark.lightdi.it.propertysourcewithvalue;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.Value;

@Configuration
@PropertySource(value = "classpath:test_properties-${lightdi.environment:default}.properties")
public class PropertySourceWithEnvironmentVariableConfiguration {
    @Value("${TEST_VALUE}")
    private String testValue;

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

}

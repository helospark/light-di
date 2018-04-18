package com.helospark.lightdi.it.multipropertyannotationcontext;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.Value;

@Configuration
@PropertySource(value = "classpath:property_file_1.properties")
@PropertySource(value = "classpath:property_file_2.properties")
public class ConfigurationWithRepeatedAnnotation {
    @Value("${FIRST_PROPERTY}")
    private String firstProperty;
    @Value("${SECOND_PROPERTY}")
    private String secondProperty;

    public String getFirstProperty() {
        return firstProperty;
    }

    public String getSecondProperty() {
        return secondProperty;
    }

}

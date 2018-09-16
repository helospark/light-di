package com.helospark.lightdi.it.plugintest.sillystringcontext;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.Value;

@Configuration
@PropertySource("classpath:property_file_1.properties")
public class SillyStringTestConfiguration {
    @Value("${FIRST_PROPERTY}")
    private String property;

    public String getProperty() {
        return property;
    }

}

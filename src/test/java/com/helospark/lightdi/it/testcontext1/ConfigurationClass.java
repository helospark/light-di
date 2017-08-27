package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class ConfigurationClass {

    @Bean
    public NonAnnotatedClass createNonAnnotatedClass() {
        return new NonAnnotatedClass();
    }
}

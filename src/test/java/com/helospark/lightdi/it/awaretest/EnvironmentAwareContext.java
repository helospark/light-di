package com.helospark.lightdi.it.awaretest;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.aware.EnvironmentAware;
import com.helospark.lightdi.properties.Environment;

@Configuration
public class EnvironmentAwareContext implements EnvironmentAware {
    private Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}

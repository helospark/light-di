package com.helospark.lightdi.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.helospark.lightdi.LightDiContext;

public class EnvironmentFactory {
    private ValueResolver valueResolver;

    public EnvironmentFactory(ValueResolver valueResolver) {
        this.valueResolver = valueResolver;
    }

    public Environment createEnvironment(LightDiContext context) {
        Environment environment = new Environment(context, valueResolver);

        environment.addPropertySource(createPropertySourceFromSystemProperties());
        environment.addPropertySource(createPropertySourceFromEnvironmentVariables());

        return environment;
    }

    private PropertySourceHolder createPropertySourceFromSystemProperties() {
        Properties properties = System.getProperties();
        Map<String, String> systemProperties = new HashMap<>();
        for (Object property : properties.keySet()) {
            String key = String.valueOf(property);
            String value = System.getProperty(key);

            systemProperties.put(key, value);
        }
        return new PropertySourceHolder(Environment.SYSTEM_PROPERTY_ORDER, systemProperties);
    }

    private PropertySourceHolder createPropertySourceFromEnvironmentVariables() {
        Map<String, String> environment = System.getenv();
        Map<String, String> environmentProperties = new HashMap<>();
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            environmentProperties.put(entry.getKey(), entry.getValue());
        }
        return new PropertySourceHolder(Environment.ENVIRONMENT_PROPERTY_ORDER, environmentProperties);
    }

}

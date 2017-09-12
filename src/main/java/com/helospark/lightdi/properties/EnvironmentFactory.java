package com.helospark.lightdi.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.helospark.lightdi.LightDiContext;

public class EnvironmentFactory {

    public Environment createEnvironment(LightDiContext context) {
        PropertyValueResolver propertyValueResolver = new PropertyValueResolver();
        PropertyStringResolver propertyStringResolver = new PropertyStringResolver(propertyValueResolver);
        ValueResolver valueResolver = new ValueResolver(propertyStringResolver);
        Environment environment = new Environment(context, valueResolver);

        environment.addPropertySource(createPropertySourceFromSystemProperties());

        return environment;
    }

    private PropertySourceHolder createPropertySourceFromSystemProperties() {
        Properties properties = System.getProperties();
        Map<String, String> propertiesInEnvironment = new HashMap<>();
        for (Object property : properties.keySet()) {
            String key = String.valueOf(property);
            String value = System.getProperty(key);

            propertiesInEnvironment.put(key, value);
        }
        return new PropertySourceHolder(Environment.SYSTEM_PROPERTY_ORDER, propertiesInEnvironment);
    }
}

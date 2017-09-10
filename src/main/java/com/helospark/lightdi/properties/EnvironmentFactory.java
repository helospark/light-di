package com.helospark.lightdi.properties;

import com.helospark.lightdi.LightDiContext;

public class EnvironmentFactory {

    public Environment createEnvironment(LightDiContext context) {
        PropertyValueResolver propertyValueResolver = new PropertyValueResolver();
        PropertyStringResolver propertyStringResolver = new PropertyStringResolver(propertyValueResolver);
        ValueResolver valueResolver = new ValueResolver(propertyStringResolver);
        return new Environment(context, valueResolver);
    }
}

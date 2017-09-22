package com.helospark.lightdi.conditional.condition;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnProperty;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.properties.Environment;

public class PropertyDependencyCondition implements DependencyCondition {
    private String propertyExpression;
    private String expectedValue;

    public PropertyDependencyCondition(ConditionalOnProperty conditionalOnProperty) {
        this.propertyExpression = conditionalOnProperty.property();
        this.expectedValue = conditionalOnProperty.havingValue();
    }

    @Override
    public boolean evaluate(LightDiContext context, SortedSet<DependencyDescriptor> dependencies) {
        Environment environment = context.getEnvironment();
        try {
            String propertyValue = environment.resolve("${" + propertyExpression + "}");
            return propertyValue.equals(expectedValue);
        } catch (Exception e) {
            return false;
        }
    }

}

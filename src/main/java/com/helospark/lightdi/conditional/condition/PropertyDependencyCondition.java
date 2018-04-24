package com.helospark.lightdi.conditional.condition;

import static com.helospark.lightdi.annotation.ConditionalOnProperty.HAVING_VALUE_ATTRIBUTE_NAME;
import static com.helospark.lightdi.annotation.ConditionalOnProperty.PROPERTY_ATTRIBUTE_NAME;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.properties.Environment;
import com.helospark.lightdi.util.LightDiAnnotation;

public class PropertyDependencyCondition implements DependencyCondition {
    private String propertyExpression;
    private String expectedValue;

    public PropertyDependencyCondition(LightDiAnnotation conditionalOnProperty) {
        this.propertyExpression = conditionalOnProperty.getAttributeAs(PROPERTY_ATTRIBUTE_NAME, String.class);
        this.expectedValue = conditionalOnProperty.getAttributeAs(HAVING_VALUE_ATTRIBUTE_NAME, String.class);
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

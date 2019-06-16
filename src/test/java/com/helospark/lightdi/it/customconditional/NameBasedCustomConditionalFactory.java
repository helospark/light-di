package com.helospark.lightdi.it.customconditional;

import java.lang.annotation.Annotation;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.conditional.AnnotationBasedConditionalProcessorFactory;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.util.LightDiAnnotation;

@Component
public class NameBasedCustomConditionalFactory implements AnnotationBasedConditionalProcessorFactory {

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ConditionalOnBeanNameContaining.class;
    }

    @Override
    public DependencyCondition getDependencyCondition(LightDiAnnotation annotation) {
        String contains = annotation.getAttributeAs("nameContains", String.class);
        return new NameBasedCondition(contains);
    }

}

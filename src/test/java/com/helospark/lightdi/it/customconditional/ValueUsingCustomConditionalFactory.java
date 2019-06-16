package com.helospark.lightdi.it.customconditional;

import java.lang.annotation.Annotation;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.conditional.AnnotationBasedConditionalProcessorFactory;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.util.LightDiAnnotation;

@Component
public class ValueUsingCustomConditionalFactory implements AnnotationBasedConditionalProcessorFactory {
    private String value;

    public ValueUsingCustomConditionalFactory(@Value("${customValue:}") String value) {
        this.value = value;
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ConditionalOnCustomValue.class;
    }

    @Override
    public DependencyCondition getDependencyCondition(LightDiAnnotation annotation) {
        return new CustomValueBasedCondition(value);
    }

}

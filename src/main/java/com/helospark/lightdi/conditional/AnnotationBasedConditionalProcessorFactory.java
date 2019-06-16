package com.helospark.lightdi.conditional;

import java.lang.annotation.Annotation;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.util.LightDiAnnotation;

public interface AnnotationBasedConditionalProcessorFactory {

    public Class<? extends Annotation> getAnnotation();

    public DependencyCondition getDependencyCondition(LightDiAnnotation annotation);

}

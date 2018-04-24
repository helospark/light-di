package com.helospark.lightdi.conditional.condition.factory;

import java.util.function.Function;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.PropertyDependencyCondition;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ConditionalOnPropertyConverter implements Function<LightDiAnnotation, DependencyCondition> {

    @Override
    public DependencyCondition apply(LightDiAnnotation annotation) {
        return new PropertyDependencyCondition(annotation);
    }
}

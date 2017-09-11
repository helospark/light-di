package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnProperty;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.PropertyDependencyCondition;

public class ConditionalOnPropertyConverter implements Function<Annotation, DependencyCondition> {

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnProperty conditionalOnBean = (ConditionalOnProperty) annotation;
        return new PropertyDependencyCondition(conditionalOnBean);
    }
}

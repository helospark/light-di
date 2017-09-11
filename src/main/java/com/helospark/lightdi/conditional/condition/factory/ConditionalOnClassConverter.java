package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.conditional.condition.ClassDependencyCondition;
import com.helospark.lightdi.conditional.condition.DependencyCondition;

public class ConditionalOnClassConverter implements Function<Annotation, DependencyCondition> {

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnClass conditionalOnBean = (ConditionalOnClass) annotation;
        return new ClassDependencyCondition(conditionalOnBean);
    }
}

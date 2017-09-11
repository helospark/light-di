package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.conditional.condition.BeanDependencyCondition;
import com.helospark.lightdi.conditional.condition.DependencyCondition;

public class ConditionalOnBeanConverter implements Function<Annotation, DependencyCondition> {

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnBean conditionalOnBean = (ConditionalOnBean) annotation;
        return new BeanDependencyCondition(conditionalOnBean);
    }
}

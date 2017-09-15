package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.conditional.condition.BeanDependencyCondition;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;

public class ConditionalOnBeanConverter implements Function<Annotation, DependencyCondition> {
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public ConditionalOnBeanConverter(BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnBean conditionalOnBean = (ConditionalOnBean) annotation;
        return new BeanDependencyCondition(conditionalOnBean, beanDependencyConditionEvaluator);
    }
}

package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnMissingBean;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.MissingBeanDependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;

public class ConditionalOnMissingBeanConverter implements Function<Annotation, DependencyCondition> {
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public ConditionalOnMissingBeanConverter(BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnMissingBean conditionalOnMissingBean = (ConditionalOnMissingBean) annotation;
        return new MissingBeanDependencyCondition(conditionalOnMissingBean, beanDependencyConditionEvaluator);
    }
}

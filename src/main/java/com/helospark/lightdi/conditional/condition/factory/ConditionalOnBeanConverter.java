package com.helospark.lightdi.conditional.condition.factory;

import java.util.function.Function;

import com.helospark.lightdi.conditional.condition.BeanDependencyCondition;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ConditionalOnBeanConverter implements Function<LightDiAnnotation, DependencyCondition> {
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public ConditionalOnBeanConverter(BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(LightDiAnnotation annotation) {
        return new BeanDependencyCondition(annotation, beanDependencyConditionEvaluator);
    }
}

package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.conditional.condition.ClassDependencyCondition;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;

public class ConditionalOnClassConverter implements Function<Annotation, DependencyCondition> {
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public ConditionalOnClassConverter(ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnClass conditionalOnBean = (ConditionalOnClass) annotation;
        return new ClassDependencyCondition(conditionalOnBean, classDependencyConditionEvaluator);
    }
}

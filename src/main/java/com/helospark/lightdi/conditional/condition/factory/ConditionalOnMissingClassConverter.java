package com.helospark.lightdi.conditional.condition.factory;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnMissingClass;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.MissingClassDependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;

public class ConditionalOnMissingClassConverter implements Function<Annotation, DependencyCondition> {
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public ConditionalOnMissingClassConverter(ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(Annotation annotation) {
        ConditionalOnMissingClass conditionalOnMissingclass = (ConditionalOnMissingClass) annotation;
        return new MissingClassDependencyCondition(conditionalOnMissingclass, classDependencyConditionEvaluator);
    }
}

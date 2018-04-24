package com.helospark.lightdi.conditional.condition.factory;

import java.util.function.Function;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.MissingClassDependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ConditionalOnMissingClassConverter implements Function<LightDiAnnotation, DependencyCondition> {
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public ConditionalOnMissingClassConverter(ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public DependencyCondition apply(LightDiAnnotation annotation) {
        return new MissingClassDependencyCondition(annotation, classDependencyConditionEvaluator);
    }
}

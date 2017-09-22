package com.helospark.lightdi.conditional.condition;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnMissingClass;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class MissingClassDependencyCondition implements DependencyCondition {
    private String className;
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public MissingClassDependencyCondition(ConditionalOnMissingClass conditionalOnMissingClass,
            ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.className = conditionalOnMissingClass.value();
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(LightDiContext context, SortedSet<DependencyDescriptor> dependencies) {
        return !classDependencyConditionEvaluator.isClassExists(className);
    }

}

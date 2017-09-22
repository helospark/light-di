package com.helospark.lightdi.conditional.condition;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ClassDependencyCondition implements DependencyCondition {
    private String className;
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public ClassDependencyCondition(ConditionalOnClass conditionalOnClass, ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.className = conditionalOnClass.value();
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(LightDiContext context, SortedSet<DependencyDescriptor> dependencies) {
        return classDependencyConditionEvaluator.isClassExists(className);
    }

}

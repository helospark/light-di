package com.helospark.lightdi.conditional.condition;

import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ClassDependencyCondition implements DependencyCondition {
    private String className;
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public ClassDependencyCondition(LightDiAnnotation conditionalOnClass, ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.className = conditionalOnClass.getAttributeAs(ConditionalOnClass.ATTRIBUTE_NAME, String.class);
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(ConditionalEvaluationRequest request) {
        return classDependencyConditionEvaluator.isClassExists(className);
    }

}

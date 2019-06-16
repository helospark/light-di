package com.helospark.lightdi.conditional.condition;

import com.helospark.lightdi.annotation.ConditionalOnMissingClass;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.util.LightDiAnnotation;

public class MissingClassDependencyCondition implements DependencyCondition {
    private String className;
    private ClassDependencyConditionEvaluator classDependencyConditionEvaluator;

    public MissingClassDependencyCondition(LightDiAnnotation annotation,
            ClassDependencyConditionEvaluator classDependencyConditionEvaluator) {
        this.className = annotation.getAttributeAs(ConditionalOnMissingClass.ATTRIBUTE_NAME, String.class);
        this.classDependencyConditionEvaluator = classDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(ConditionalEvaluationRequest request) {
        return !classDependencyConditionEvaluator.isClassExists(className);
    }

}

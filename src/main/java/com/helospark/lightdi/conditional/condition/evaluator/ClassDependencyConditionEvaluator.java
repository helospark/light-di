package com.helospark.lightdi.conditional.condition.evaluator;

public class ClassDependencyConditionEvaluator {

    public boolean isClassExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

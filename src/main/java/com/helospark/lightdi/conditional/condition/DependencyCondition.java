package com.helospark.lightdi.conditional.condition;

public interface DependencyCondition {

    public boolean evaluate(ConditionalEvaluationRequest request);

}

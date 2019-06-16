package com.helospark.lightdi.it.customconditional;

import com.helospark.lightdi.conditional.condition.ConditionalEvaluationRequest;
import com.helospark.lightdi.conditional.condition.DependencyCondition;

public class CustomValueBasedCondition implements DependencyCondition {
    private String value;

    public CustomValueBasedCondition(String value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(ConditionalEvaluationRequest request) {
        return this.value.equals("create");
    }

}

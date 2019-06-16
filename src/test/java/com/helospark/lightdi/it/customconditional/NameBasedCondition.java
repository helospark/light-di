package com.helospark.lightdi.it.customconditional;

import com.helospark.lightdi.conditional.condition.ConditionalEvaluationRequest;
import com.helospark.lightdi.conditional.condition.DependencyCondition;

public class NameBasedCondition implements DependencyCondition {
    private String contains;

    public NameBasedCondition(String contains) {
        this.contains = contains.toLowerCase();
    }

    @Override
    public boolean evaluate(ConditionalEvaluationRequest request) {
        return request.getDescriptor().getQualifier().toLowerCase().contains(contains);
    }

}

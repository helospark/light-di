package com.helospark.lightdi.conditional.condition;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ClassDependencyCondition implements DependencyCondition {
    private String className;

    public ClassDependencyCondition(ConditionalOnClass conditionalOnClass) {
        this.className = conditionalOnClass.value();
    }

    @Override
    public boolean evaluate(LightDiContext context, List<DependencyDescriptor> dependencies) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}

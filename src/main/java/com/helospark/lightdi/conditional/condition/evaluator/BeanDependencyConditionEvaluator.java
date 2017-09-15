package com.helospark.lightdi.conditional.condition.evaluator;

import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class BeanDependencyConditionEvaluator {

    public boolean isBeanExists(DependencyDescriptorQuery dependencyDescriptorQuery, List<DependencyDescriptor> dependencies) {
        return dependencies.stream()
                .filter(dependency -> dependency.doesMatch(dependencyDescriptorQuery))
                .findFirst()
                .isPresent();
    }
}

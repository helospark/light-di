package com.helospark.lightdi.conditional.condition.evaluator;

import java.util.SortedSet;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class BeanDependencyConditionEvaluator {

    public boolean isBeanExists(DependencyDescriptorQuery dependencyDescriptorQuery, SortedSet<DependencyDescriptor> dependencies) {
        return dependencies.stream()
                .filter(dependency -> dependency.doesMatch(dependencyDescriptorQuery))
                .findFirst()
                .isPresent();
    }
}

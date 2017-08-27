package com.helospark.lightdi.dependencywire;

import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class FindInDependencySupport {

    public DependencyDescriptor findOrThrow(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptorQuery query) {
        List<DependencyDescriptor> found = dependencyDescriptors.stream()
                .filter(descriptor -> descriptor.doesMatch(query))
                .collect(Collectors.toList());
        if (found.size() != 1) {
            throw new IllegalArgumentException("No single match for " + query + " found: " + found);
        }
        return found.get(0);
    }
}

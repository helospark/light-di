package com.helospark.lightdi.dependencywire;

import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class FindInDependencySupport {

    public DependencyDescriptor findOrThrow(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptorQuery query) {
        List<DependencyDescriptor> found = dependencyDescriptors.stream()
                .filter(descriptor -> isMatch(descriptor, query))
                .collect(Collectors.toList());
        if (found.size() != 1) {
            throw new IllegalArgumentException("No single match " + found);
        }
        return found.get(0);
    }

    private boolean isMatch(DependencyDescriptor descriptor, DependencyDescriptorQuery query) {
        return descriptor.getClazz().equals(query.getClazz())
                && query.getQualifier().map(a -> a.equals(descriptor.getQualifier())).orElse(true);
    }
}

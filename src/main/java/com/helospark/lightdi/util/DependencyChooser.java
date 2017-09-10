package com.helospark.lightdi.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class DependencyChooser {

    public static DependencyDescriptor findDependencyFromQuery(Collection<DependencyDescriptor> dependencies, DependencyDescriptorQuery toFind) {
        List<DependencyDescriptor> found = findDependencyDescriptor(dependencies, toFind);
        return findDependencyToGenerate(found);
    }

    public static List<DependencyDescriptor> findDependencyDescriptor(Collection<DependencyDescriptor> dependencies,
            DependencyDescriptorQuery toFind) {
        return dependencies
                .stream()
                .filter(dependencyEntry -> dependencyEntry.doesMatch(toFind))
                .collect(Collectors.toList());
    }

    public static DependencyDescriptor findDependencyToGenerate(List<DependencyDescriptor> dependencyToCreate) {
        Optional<DependencyDescriptor> primary = findPrimary(dependencyToCreate);
        if (dependencyToCreate.size() == 1) {
            return dependencyToCreate.get(0);
        } else if (primary.isPresent()) {
            return primary.get();
        } else {
            throw new IllegalArgumentException("No single match for found " + dependencyToCreate);
        }
    }

    public static Optional<DependencyDescriptor> findPrimary(
            List<DependencyDescriptor> foundDependencies) {
        return foundDependencies.stream()
                .filter(dependency -> dependency.isPrimary())
                .findFirst();
    }
}

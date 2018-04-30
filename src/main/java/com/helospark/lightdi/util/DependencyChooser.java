package com.helospark.lightdi.util;

import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class DependencyChooser {

    public static DependencyDescriptor findDependencyFromQuery(Collection<DependencyDescriptor> dependencies, DependencyDescriptorQuery toFind) {
        SortedSet<DependencyDescriptor> found = findDependencyDescriptor(dependencies, toFind);
        return findDependencyToGenerate(found, toFind);
    }

    public static SortedSet<DependencyDescriptor> findDependencyDescriptor(Collection<DependencyDescriptor> dependencies,
            DependencyDescriptorQuery toFind) {
        return dependencies
                .stream()
                .filter(dependencyEntry -> dependencyEntry.doesMatch(toFind))
                .collect(Collectors.toCollection(() -> new TreeSet<>()));
    }

    public static DependencyDescriptor findDependencyToGenerate(SortedSet<DependencyDescriptor> dependencyToCreate,
            DependencyDescriptorQuery toFind) {
        if (dependencyToCreate.size() == 1) {
            return dependencyToCreate.first();
        } else {
            Optional<DependencyDescriptor> primary = findPrimary(dependencyToCreate);
            if (primary.isPresent()) {
                return primary.get();
            } else if (!toFind.isRequired()) {
                return null;
            } else {
                throw new IllegalArgumentException("No single match for found for " + toFind + ", found " + dependencyToCreate);
            }
        }

    }

    public static Optional<DependencyDescriptor> findPrimary(
            SortedSet<DependencyDescriptor> foundDependencies) {
        return foundDependencies.stream()
                .filter(dependency -> dependency.isPrimary())
                .findFirst();
    }
}

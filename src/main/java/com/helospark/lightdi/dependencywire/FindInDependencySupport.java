package com.helospark.lightdi.dependencywire;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyCollectionDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.DependencyChooser;

public class FindInDependencySupport {

    public DependencyDescriptor findOrThrow(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptorQuery query) {
        return DependencyChooser.findDependencyFromQuery(dependencyDescriptors, query);
    }

    public InjectionDescriptor findListOrEmpty(List<DependencyDescriptor> dependencyDescriptors, DependencyDescriptorQuery query,
            Class<? extends Collection<?>> collectionType) {
        List<DependencyDescriptor> matchingDependencies = dependencyDescriptors.stream()
                .filter(descriptor -> descriptor.doesMatch(query))
                .collect(Collectors.toList());
        return DependencyCollectionDescriptor.builder()
                .withDependencies(matchingDependencies)
                .withCollectionType(collectionType)
                .build();
    }
}

package com.helospark.lightdi.dependencywire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.descriptor.DependencyCollectionDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.NullInjectDescriptor;
import com.helospark.lightdi.util.DependencyChooser;
import com.helospark.lightdi.util.ReflectionUtil;

public class FindInDependencySupport {
    private DependencyChooser dependencyChooser;

    public FindInDependencySupport(DependencyChooser dependencyChooser) {
        this.dependencyChooser = dependencyChooser;
    }

    public InjectionDescriptor find(SortedSet<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptorQuery query) {
        DependencyDescriptor findDependencyFromQuery = dependencyChooser.findDependencyFromQuery(dependencyDescriptors, query);
        if (findDependencyFromQuery == null) {
            return new NullInjectDescriptor();
        } else {
            return findDependencyFromQuery;
        }
    }

    public InjectionDescriptor findListOrEmpty(SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptorQuery query,
            Class<? extends Collection<?>> collectionType) {

        List<DependencyDescriptor> matchingDependencies = new ArrayList<>();
        for (DependencyDescriptor descriptor : dependencyDescriptors) {
            if (descriptor.doesMatch(query)) {
                matchingDependencies.add(descriptor);
            }
            boolean isGenericParameterMatch = descriptor.getGenericType()
                    .flatMap(a -> ReflectionUtil.extractGenericTypeFromType(a))
                    .map(a -> query.getClazz().get().isAssignableFrom(a))
                    .orElse(false);
            if (isGenericParameterMatch) {
                matchingDependencies.add(descriptor);
            }
        }
        return DependencyCollectionDescriptor.builder()
                .withDependencies(matchingDependencies)
                .withCollectionType(collectionType)
                .build();
    }
}

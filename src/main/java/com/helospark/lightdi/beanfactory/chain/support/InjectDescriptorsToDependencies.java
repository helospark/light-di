package com.helospark.lightdi.beanfactory.chain.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.descriptor.DependencyCollectionDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class InjectDescriptorsToDependencies {
    public List<DependencyDescriptor> extract(List<InjectionDescriptor> list) {
        List<DependencyDescriptor> result = new ArrayList<>();
        list.stream()
                .filter(injectDescriptor -> injectDescriptor instanceof DependencyDescriptor)
                .map(injectDescriptor -> (DependencyDescriptor) injectDescriptor)
                .forEach(dependency -> result.add(dependency));
        list.stream()
                .filter(injectDescriptor -> injectDescriptor instanceof DependencyCollectionDescriptor)
                .flatMap(injectDescriptor -> ((DependencyCollectionDescriptor) injectDescriptor).getDependencies().stream())
                .forEach(dependency -> result.add(dependency));
        return result;
    }

    public Collection<? extends DependencyDescriptor> extract(Stream<InjectionDescriptor> stream) {
        return extract(stream.collect(Collectors.toList()));
    }
}

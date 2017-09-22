package com.helospark.lightdi.dependencywire;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ComponentScanCollector {

    public List<String> collectComponentScan(SortedSet<DependencyDescriptor> dependencyDescriptors) {
        return dependencyDescriptors.stream()
                .flatMap(descriptor -> addClasses(descriptor))
                .distinct()
                .collect(Collectors.toList());
    }

    private Stream<String> addClasses(DependencyDescriptor descriptor) {
        ComponentScan componentScan = descriptor.getClazz().getAnnotation(ComponentScan.class);
        if (componentScan == null) {
            return Stream.empty();
        } else {
            if (componentScan.value().length > 0) {
                return Arrays.stream(componentScan.value());
            } else if (componentScan.basePackageClasses().length > 0) {
                return Arrays.stream(componentScan.basePackageClasses()).map(clazz -> clazz.getPackage().getName());
            } else {
                return Stream.of(descriptor.getClazz().getPackage().getName());
            }
        }
    }
}

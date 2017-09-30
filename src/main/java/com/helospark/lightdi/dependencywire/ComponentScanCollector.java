package com.helospark.lightdi.dependencywire;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class ComponentScanCollector {

    public List<ComponentScanPackage> collectComponentScan(SortedSet<DependencyDescriptor> dependencyDescriptors) {
        return dependencyDescriptors.stream()
                .flatMap(descriptor -> addClasses(descriptor))
                .distinct()
                .collect(Collectors.toList());
    }

    private Stream<ComponentScanPackage> addClasses(DependencyDescriptor descriptor) {
        Set<ComponentScan> annotations = AnnotationUtil.getAnnotationsOfType(descriptor.getClazz(), ComponentScan.class);
        if (annotations.isEmpty()) {
            return Stream.empty();
        } else {
            return annotations.stream()
                    .flatMap(componentScan -> processComponentScan(componentScan, descriptor));
        }
    }

    private Stream<ComponentScanPackage> processComponentScan(ComponentScan componentScan, DependencyDescriptor descriptor) {
        if (componentScan.value().length > 0) {
            return Arrays.stream(componentScan.value())
                    .map(packageName -> packageToComponentScanPackage(packageName, componentScan, descriptor));
        } else if (componentScan.basePackageClasses().length > 0) {
            return Arrays.stream(componentScan.basePackageClasses())
                    .map(clazz -> packageToComponentScanPackage(clazz.getPackage().getName(), componentScan, descriptor));
        } else {
            return Stream.of(packageToComponentScanPackage(descriptor.getClazz().getPackage().getName(), componentScan, descriptor));
        }
    }

    private ComponentScanPackage packageToComponentScanPackage(String packageName, ComponentScan componentScan, DependencyDescriptor descriptor) {
        return ComponentScanPackage.builder()
                .withPackageName(packageName)
                .withRootClass(descriptor.getClazz())
                .withOnlyCurrentJar(componentScan.onlyScanThisJar())
                .build();
    }
}

package com.helospark.lightdi.dependencywire;

import static com.helospark.lightdi.annotation.ComponentScan.BASE_PACKAGE_ATTRIBUTE_NAME;
import static com.helospark.lightdi.annotation.ComponentScan.VALUE_ATTRIBUTE_NAME;

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
import com.helospark.lightdi.util.LightDiAnnotation;

public class ComponentScanCollector {

    public List<ComponentScanPackage> collectComponentScan(SortedSet<DependencyDescriptor> dependencyDescriptors) {
        return dependencyDescriptors.stream()
                .flatMap(descriptor -> addClasses(descriptor))
                .distinct()
                .collect(Collectors.toList());
    }

    private Stream<ComponentScanPackage> addClasses(DependencyDescriptor descriptor) {
        Set<LightDiAnnotation> annotations = AnnotationUtil.getAnnotationsOfType(descriptor.getClazz(), ComponentScan.class);
        if (annotations.isEmpty()) {
            return Stream.empty();
        } else {
            return annotations.stream()
                    .flatMap(componentScan -> processComponentScan(componentScan, descriptor));
        }
    }

    private Stream<ComponentScanPackage> processComponentScan(LightDiAnnotation componentScan, DependencyDescriptor descriptor) {
        String[] value = componentScan.getAttributeAs(VALUE_ATTRIBUTE_NAME, String[].class);
        Class<?>[] basePackages = componentScan.getAttributeAs(BASE_PACKAGE_ATTRIBUTE_NAME, Class[].class);
        if (value.length > 0) {
            return Arrays.stream(value)
                    .map(packageName -> packageToComponentScanPackage(packageName, componentScan, descriptor));
        } else if (basePackages.length > 0) {
            return Arrays.stream(basePackages)
                    .map(clazz -> packageToComponentScanPackage(clazz.getPackage().getName(), componentScan, descriptor));
        } else {
            return Stream.of(packageToComponentScanPackage(descriptor.getClazz().getPackage().getName(), componentScan, descriptor));
        }

    }

    private ComponentScanPackage packageToComponentScanPackage(String packageName, LightDiAnnotation componentScan, DependencyDescriptor descriptor) {
        return ComponentScanPackage.builder()
                .withPackageName(packageName)
                .build();
    }
}

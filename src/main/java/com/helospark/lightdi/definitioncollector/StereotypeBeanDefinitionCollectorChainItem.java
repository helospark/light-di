package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;
import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;

import java.util.Collections;
import java.util.List;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Service;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.util.IsLazyExtractor;
import com.helospark.lightdi.util.QualifierExtractor;

public class StereotypeBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        DependencyDescriptor dependencyDescriptor = StereotypeDependencyDescriptor.builder()
                .withClazz(clazz)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(clazz))
                .withScope(QualifierExtractor.extractScope(clazz))
                .withIsLazy(IsLazyExtractor.isLazy(clazz))
                .build();
        return Collections.singletonList(dependencyDescriptor);
    }

    @Override
    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Component.class) ||
                hasAnnotation(clazz, Service.class);
    }
}

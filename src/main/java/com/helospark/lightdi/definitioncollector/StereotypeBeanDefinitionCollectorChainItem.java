package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;
import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;

import java.util.Collections;
import java.util.List;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Service;
import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractor;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.util.BeanOrderExtractor;
import com.helospark.lightdi.util.IsLazyExtractor;
import com.helospark.lightdi.util.IsPrimaryExtractor;
import com.helospark.lightdi.util.QualifierExtractor;

public class StereotypeBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {
    private ConditionalAnnotationsExtractor conditionalAnnotationsExtractor;

    public StereotypeBeanDefinitionCollectorChainItem(ConditionalAnnotationsExtractor conditionalAnnotationsExtractor) {
        this.conditionalAnnotationsExtractor = conditionalAnnotationsExtractor;
    }

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        if (isSupported(clazz)) {
            return collectDefinitionsOnNonStereotypeClass(clazz);
        } else {
            return Collections.emptyList();
        }
    }

    public List<DependencyDescriptor> collectDefinitionsOnNonStereotypeClass(Class<?> clazz) {
        DependencyDescriptor dependencyDescriptor = StereotypeDependencyDescriptor.builder()
                .withClazz(clazz)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(clazz))
                .withScope(QualifierExtractor.extractScope(clazz))
                .withIsLazy(IsLazyExtractor.isLazy(clazz))
                .withIsPrimary(IsPrimaryExtractor.isPrimary(clazz))
                .withConditions(conditionalAnnotationsExtractor.extractConditions(clazz))
                .withOrder(BeanOrderExtractor.extractOrder(clazz))
                .build();
        return Collections.singletonList(dependencyDescriptor);
    }

    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Component.class) ||
                hasAnnotation(clazz, Service.class);
    }
}

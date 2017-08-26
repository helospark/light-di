package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.util.Collections;
import java.util.List;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Service;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class StereotypeBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        DependencyDescriptor dependencyDescriptor = DependencyDescriptor.builder()
                .withClazz(clazz)
                .build();
        return Collections.singletonList(dependencyDescriptor);
    }

    @Override
    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Component.class) ||
                hasAnnotation(clazz, Service.class);
    }
}

package com.helospark.lightdi.definitioncollector;

import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface BeanDefinitionCollectorChainItem {

    List<DependencyDescriptor> collectDefinitions(Class<?> clazz);

}
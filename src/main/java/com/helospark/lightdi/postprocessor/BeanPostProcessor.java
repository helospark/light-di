package com.helospark.lightdi.postprocessor;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface BeanPostProcessor {

    Object postProcessAfterInitialization(Object bean, DependencyDescriptor dependencyDescriptor);

}

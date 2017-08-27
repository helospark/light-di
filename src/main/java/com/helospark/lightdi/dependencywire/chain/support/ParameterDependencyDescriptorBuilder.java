package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Parameter;
import java.util.List;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.dependencywire.FindInDependencySupport;
import com.helospark.lightdi.dependencywire.PropertyDescriptorFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class ParameterDependencyDescriptorBuilder {
    private FindInDependencySupport findInDependencySupport;
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public ParameterDependencyDescriptorBuilder(FindInDependencySupport findInDependencySupport,
            PropertyDescriptorFactory propertyDescriptorFactory) {
        this.findInDependencySupport = findInDependencySupport;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
    }

    public InjectionDescriptor build(Parameter parameter, List<DependencyDescriptor> dependencyDescriptors) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(parameter.getType())
                .build();

        if (AnnotationUtil.hasAnnotation(parameter, Value.class)) {
            return propertyDescriptorFactory.buildPropertyDescriptor(parameter);
        } else {
            return findInDependencySupport.findOrThrow(dependencyDescriptors, query);
        }
    }
}

package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class MethodDependencyCollector {
    private DependencyDescriptorBuilder parameterDependencyDescriptorBuilder;

    public MethodDependencyCollector(DependencyDescriptorBuilder parameterDependencyDescriptorBuilder) {
        this.parameterDependencyDescriptorBuilder = parameterDependencyDescriptorBuilder;
    }

    public List<InjectionDescriptor> getSetterDependencies(Method method,
            List<DependencyDescriptor> dependencyDescriptors) {
        return Arrays.stream(method.getParameters())
                .map(methodParam -> collectDependencyFor(method, methodParam, dependencyDescriptors))
                .collect(Collectors.toList());
    }

    private InjectionDescriptor collectDependencyFor(Method method, Parameter parameter,
            List<DependencyDescriptor> dependencyDescriptors) {
        boolean isRequired = extractRequired(method);
        return parameterDependencyDescriptorBuilder.build(parameter,
                dependencyDescriptors, isRequired);
    }

    private boolean extractRequired(Method method) {
        Autowired annotation = method.getAnnotation(Autowired.class);
        if (annotation != null) {
            return annotation.required();
        } else {
            return true;
        }
    }

}

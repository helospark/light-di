package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class MethodDependencyCollector {
    private ParameterDependencyDescriptorBuilder parameterDependencyDescriptorBuilder;

    public MethodDependencyCollector(ParameterDependencyDescriptorBuilder parameterDependencyDescriptorBuilder) {
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
        return parameterDependencyDescriptorBuilder.build(parameter,
                dependencyDescriptors);
    }

}

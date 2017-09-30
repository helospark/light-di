package com.helospark.lightdi.dependencywire.chain.support;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class MethodDependencyCollector {
    private DependencyDescriptorBuilder parameterDependencyDescriptorBuilder;

    public MethodDependencyCollector(DependencyDescriptorBuilder parameterDependencyDescriptorBuilder) {
        this.parameterDependencyDescriptorBuilder = parameterDependencyDescriptorBuilder;
    }

    public List<InjectionDescriptor> getSetterDependencies(Method method,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        return Arrays.stream(method.getParameters())
                .map(methodParam -> collectDependencyFor(method, methodParam, dependencyDescriptors))
                .collect(Collectors.toList());
    }

    private InjectionDescriptor collectDependencyFor(Method method, Parameter parameter,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        boolean isRequired = extractRequired(method);
        return parameterDependencyDescriptorBuilder.build(parameter,
                dependencyDescriptors, isRequired);
    }

    private boolean extractRequired(Method method) {
        if (hasAnnotation(method, Autowired.class)) {
            return AnnotationUtil.getSingleAnnotationOfType(method, Autowired.class).required();
        } else {
            return true;
        }
    }

}

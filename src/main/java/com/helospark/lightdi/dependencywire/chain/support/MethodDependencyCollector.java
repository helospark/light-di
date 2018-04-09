package com.helospark.lightdi.dependencywire.chain.support;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.dependencywire.chain.support.domain.CompatibleParameter;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class MethodDependencyCollector {
    private DependencyDescriptorBuilder parameterDependencyDescriptorBuilder;
    private CompatibleParameterFactory compatibleParameterFactory;

    public MethodDependencyCollector(DependencyDescriptorBuilder parameterDependencyDescriptorBuilder,
            CompatibleParameterFactory compatibleParameterFactory) {
        this.parameterDependencyDescriptorBuilder = parameterDependencyDescriptorBuilder;
        this.compatibleParameterFactory = compatibleParameterFactory;
    }

    public List<InjectionDescriptor> getSetterDependencies(Method method,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<InjectionDescriptor> result = new ArrayList<>();
        for (int i = 0; i < method.getParameterCount(); ++i) {
            CompatibleParameter methodParameter = compatibleParameterFactory.createParameter(method, i);
            result.add(collectDependencyFor(method, methodParameter, dependencyDescriptors));
        }
        return result;
    }

    private InjectionDescriptor collectDependencyFor(Method method, CompatibleParameter parameter,
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

package com.helospark.lightdi.dependencywire.chain.support;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.dependencywire.chain.support.domain.CustomParameter;
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
            SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependencyToCreate) {
        List<InjectionDescriptor> result = new ArrayList<>();
        for (int i = 0; i < method.getParameterTypes().length; ++i) {
            CustomParameter methodParameter = compatibleParameterFactory.createParameter(method, i);
            result.add(collectDependencyFor(method, methodParameter, dependencyDescriptors, dependencyToCreate));
        }
        return result;
    }

    private InjectionDescriptor collectDependencyFor(Method method, CustomParameter parameter,
            SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependencyToCreate) {
        boolean isRequired = extractRequired(method);
        return parameterDependencyDescriptorBuilder.build(parameter,
                dependencyDescriptors, isRequired, dependencyToCreate);
    }

    private boolean extractRequired(Method method) {
        if (hasAnnotation(method, Autowired.class)) {
            return AnnotationUtil.getSingleAnnotationOfType(method, Autowired.class).getAttributeAs(Autowired.REQUIRED_ATTRIBUTE_NAME, Boolean.class);
        } else {
            return true;
        }
    }

}

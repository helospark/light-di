package com.helospark.lightdi.dependencywire.chain.support;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.dependencywire.PropertyDescriptorFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;
import com.helospark.lightdi.util.ReflectionUtil;

public class SetterWireSupport {
    private PropertyDescriptorFactory propertyDescriptorFactory;
    private MethodDependencyCollector methodDependencyCollector;

    public SetterWireSupport(PropertyDescriptorFactory propertyDescriptorFactory,
            MethodDependencyCollector methodDependencyCollector) {
        this.propertyDescriptorFactory = propertyDescriptorFactory;
        this.methodDependencyCollector = methodDependencyCollector;
    }

    public List<MethodDescriptor> getSetterDependencies(Class<?> clazz,
            SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependencyToCreate) {
        List<MethodDescriptor> result = new ArrayList<>();
        result.addAll(collectInjectMethods(clazz, dependencyDescriptors, dependencyToCreate));
        result.addAll(collectValueMethods(clazz, dependencyDescriptors));
        return result;
    }

    private List<MethodDescriptor> collectInjectMethods(Class<?> clazz,
            SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependencyToCreate) {
        List<MethodDescriptor> result = new ArrayList<>();
        List<Method> setters = ReflectionUtil.getNonObjectMethods(clazz)
                .filter(method -> isAutowiredSetter(method))
                .collect(Collectors.toList());

        for (Method method : setters) {
            List<InjectionDescriptor> injections = methodDependencyCollector.getSetterDependencies(method,
                    dependencyDescriptors, dependencyToCreate);
            result.add(MethodDescriptor.builder()
                    .withMethod(method)
                    .withInjectionDescriptor(injections)
                    .build());
        }
        return result;
    }

    private List<MethodDescriptor> collectValueMethods(Class<?> clazz,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<MethodDescriptor> result = new ArrayList<>();
        List<Method> setters = ReflectionUtil.getNonObjectMethods(clazz)
                .filter(method -> isValueSetter(method))
                .collect(Collectors.toList());

        for (Method method : setters) {
            result.add(MethodDescriptor.builder()
                    .withMethod(method)
                    .withInjectionDescriptor(
                            Collections.singletonList(propertyDescriptorFactory.buildPropertyDescriptor(method)))
                    .build());
        }
        return result;
    }

    private boolean isAutowiredSetter(Method method) {
        return hasAnnotation(method, Autowired.class) && hasSetterName(method);
    }

    private boolean isValueSetter(Method method) {
        return hasAnnotation(method, Value.class) && hasSetterName(method);
    }

    private boolean hasSetterName(Method method) {
        String methodName = method.getName();
        return methodName.startsWith("set") || methodName.startsWith("is");
    }

}

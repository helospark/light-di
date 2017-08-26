package com.helospark.lightdi.dependencywire;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.setter.SetterDescriptor;

public class SetterWireSupport {
    private FindInDependencySupport findInDependencySupport;
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public SetterWireSupport(FindInDependencySupport findInDependencySupport,
            PropertyDescriptorFactory propertyDescriptorFactory) {
        this.findInDependencySupport = findInDependencySupport;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
    }

    public List<SetterDescriptor> getSetterDependencies(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<SetterDescriptor> result = new ArrayList<>();
        result.addAll(collectInjectMethods(clazz, dependencyDescriptors));
        result.addAll(collectValueMethods(clazz, dependencyDescriptors));
        return result;
    }

    private List<SetterDescriptor> collectInjectMethods(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<SetterDescriptor> result = new ArrayList<>();
        List<Method> setters = Arrays.stream(clazz.getMethods())
                .filter(method -> isAutowiredSetter(method))
                .collect(Collectors.toList());

        for (Method method : setters) {
            Class<?> paramType = method.getParameterTypes()[0];
            DependencyDescriptorQuery query = DependencyDescriptorQuery
                    .builder()
                    .withClazz(paramType)
                    .build();
            DependencyDescriptor dependencyDescriptor = findInDependencySupport.findOrThrow(dependencyDescriptors,
                    query);
            result.add(SetterDescriptor.builder()
                    .withInjectionDescriptor(dependencyDescriptor)
                    .withMethod(method)
                    .build());
        }
        return result;
    }

    private List<SetterDescriptor> collectValueMethods(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<SetterDescriptor> result = new ArrayList<>();
        List<Method> setters = Arrays.stream(clazz.getMethods())
                .filter(method -> isValueSetter(method))
                .collect(Collectors.toList());

        for (Method method : setters) {
            result.add(SetterDescriptor.builder()
                    .withMethod(method)
                    .withInjectionDescriptor(propertyDescriptorFactory.buildPropertyDescriptor(method))
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

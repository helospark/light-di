package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class ConstructorWireSupport {
    private static final boolean CONSTRUCTOR_PARAMETER_REQUIRED = true;
    private DependencyDescriptorBuilder parameterDependencyDescriptorBuilder;

    public ConstructorWireSupport(DependencyDescriptorBuilder parameterDependencyDescriptorBuilder) {
        this.parameterDependencyDescriptorBuilder = parameterDependencyDescriptorBuilder;
    }

    public List<ConstructorDescriptor> getConstructors(SortedSet<DependencyDescriptor> dependencyDescriptors,
            Class<?> clazz) {
        List<ConstructorDescriptor> constructorDescriptors = new ArrayList<>();
        Optional<Constructor<?>> constructorToUseOptional = getConstructorToUse(clazz);
        if (constructorToUseOptional.isPresent()) {
            Constructor<?> constructorToUse = constructorToUseOptional.get();
            Class<?>[] parameters = constructorToUse.getParameterTypes();
            for (int i = 0; i < parameters.length; ++i) {
                Parameter parameter = constructorToUse.getParameters()[i];
                InjectionDescriptor injectDescriptor = parameterDependencyDescriptorBuilder.build(parameter,
                        dependencyDescriptors, CONSTRUCTOR_PARAMETER_REQUIRED);

                constructorDescriptors.add(ConstructorDescriptor.builder()
                        .withIndex(i)
                        .withDependencyDescriptor(injectDescriptor)
                        .withConstructor(constructorToUse)
                        .build());
            }
        }
        return constructorDescriptors;

    }

    private Optional<Constructor<?>> getConstructorToUse(Class<?> clazz) {
        List<Constructor<?>> constructors = Arrays.asList(clazz.getConstructors());
        if (constructors.size() == 1) {
            return Optional.of(constructors.get(0));
        } else if (constructors.size() > 1) {
            List<Constructor<?>> autowiredAnnotatedConstructors = constructors.stream()
                    .filter(constructor -> AnnotationUtil.hasAnnotation(constructor, Autowired.class))
                    .collect(Collectors.toList());
            if (autowiredAnnotatedConstructors.size() == 1) {
                return Optional.of(autowiredAnnotatedConstructors.get(0));
            } else {
                throw new IllegalArgumentException("No unambiguous constructor found for " + clazz
                        + ", either create one constructor only or annotatate only one constructor with @Autowired");
            }
        }
        return Optional.empty();
    }

}

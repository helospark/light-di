package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;

public class ConstructorWireSupport {
    private static final boolean CONSTRUCTOR_PARAMETER_REQUIRED = true;
    private DependencyDescriptorBuilder parameterDependencyDescriptorBuilder;

    public ConstructorWireSupport(DependencyDescriptorBuilder parameterDependencyDescriptorBuilder) {
        this.parameterDependencyDescriptorBuilder = parameterDependencyDescriptorBuilder;
    }

    public List<ConstructorDescriptor> getConstructors(List<DependencyDescriptor> dependencyDescriptors,
            Class<?> clazz) {
        List<Constructor<?>> constructors = Arrays.asList(clazz.getConstructors());
        List<ConstructorDescriptor> cons = new ArrayList<>();
        if (constructors.size() > 1) {
            throw new IllegalArgumentException("Only single constructor supported at the moment");
        } else if (constructors.size() == 1) {
            Constructor<?> constructorToUse = constructors.get(0);
            Class<?>[] parameters = constructorToUse.getParameterTypes();
            for (int i = 0; i < parameters.length; ++i) {
                Parameter parameter = constructorToUse.getParameters()[i];
                InjectionDescriptor injectDescriptor = parameterDependencyDescriptorBuilder.build(parameter,
                        dependencyDescriptors, CONSTRUCTOR_PARAMETER_REQUIRED);

                cons.add(ConstructorDescriptor.builder()
                        .withIndex(i)
                        .withDependencyDescriptor(injectDescriptor)
                        .build());
            }
        }
        return cons;
    }

}

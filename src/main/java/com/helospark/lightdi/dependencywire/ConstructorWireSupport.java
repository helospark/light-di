package com.helospark.lightdi.dependencywire;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.constructor.ConstructorDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class ConstructorWireSupport {
    private FindInDependencySupport findInDependencySupport;
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public ConstructorWireSupport(FindInDependencySupport findInDependencySupport,
            PropertyDescriptorFactory propertyDescriptorFactory) {
        this.findInDependencySupport = findInDependencySupport;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
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
                DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                        .withClazz(parameters[i])
                        .build();

                if (AnnotationUtil.hasAnnotation(parameter, Value.class)) {
                    cons.add(ConstructorDescriptor.builder()
                            .withIndex(i)
                            .withDependencyDescriptor(propertyDescriptorFactory.buildPropertyDescriptor(parameter))
                            .build());
                } else {
                    DependencyDescriptor dependencyDescriptor = findInDependencySupport.findOrThrow(
                            dependencyDescriptors,
                            query);
                    cons.add(ConstructorDescriptor.builder()
                            .withDependencyDescriptor(dependencyDescriptor)
                            .withIndex(i)
                            .build());
                }
            }
        }
        return cons;
    }

}

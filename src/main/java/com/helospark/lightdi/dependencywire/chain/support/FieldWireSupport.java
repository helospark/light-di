package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class FieldWireSupport {
    private DependencyDescriptorBuilder dependencyDescriptorBuilder;

    public FieldWireSupport(DependencyDescriptorBuilder dependencyDescriptorBuilder) {
        this.dependencyDescriptorBuilder = dependencyDescriptorBuilder;
    }

    public List<FieldDescriptor> getFieldDependencies(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        result.addAll(collectAutowiredFields(clazz, dependencyDescriptors));
        result.addAll(collectValueFields(clazz, dependencyDescriptors));
        return result;
    }

    private List<FieldDescriptor> collectAutowiredFields(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> isAnnotated(field))
                .collect(Collectors.toList());

        for (Field field : fields) {
            InjectionDescriptor injectDescriptor = dependencyDescriptorBuilder.build(field, dependencyDescriptors,
                    field.getAnnotation(Autowired.class).required());
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withInjectionDescriptor(injectDescriptor)
                    .build());
        }
        return result;
    }

    private List<FieldDescriptor> collectValueFields(Class<?> clazz,
            List<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> AnnotationUtil.hasAnnotation(field, Value.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            InjectionDescriptor injectDescriptor = dependencyDescriptorBuilder.build(field, dependencyDescriptors,
                    field.getAnnotation(Value.class).required());
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withInjectionDescriptor(injectDescriptor)
                    .build());
        }
        return result;
    }

    private boolean isAnnotated(Field field) {
        return field.getAnnotationsByType(Autowired.class).length > 0;
    }
}

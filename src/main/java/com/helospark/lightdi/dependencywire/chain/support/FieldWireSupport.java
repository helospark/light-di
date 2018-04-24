package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
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
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        result.addAll(collectAutowiredFields(clazz, dependencyDescriptors));
        result.addAll(collectValueFields(clazz, dependencyDescriptors));
        return result;
    }

    private List<FieldDescriptor> collectAutowiredFields(Class<?> clazz,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> isAnnotated(field))
                .collect(Collectors.toList());

        for (Field field : fields) {
            InjectionDescriptor injectDescriptor = dependencyDescriptorBuilder.build(field, dependencyDescriptors,
                    AnnotationUtil.getSingleAnnotationOfType(field, Autowired.class).getAttributeAs(Autowired.REQUIRED_ATTRIBUTE_NAME,
                            Boolean.class));
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withInjectionDescriptor(injectDescriptor)
                    .build());
        }
        return result;
    }

    private List<FieldDescriptor> collectValueFields(Class<?> clazz,
            SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<FieldDescriptor> result = new ArrayList<>();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> AnnotationUtil.hasAnnotation(field, Value.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            InjectionDescriptor injectDescriptor = dependencyDescriptorBuilder.build(field, dependencyDescriptors,
                    AnnotationUtil.getSingleAnnotationOfType(field, Value.class).getAttributeAs(Autowired.REQUIRED_ATTRIBUTE_NAME, Boolean.class));
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withInjectionDescriptor(injectDescriptor)
                    .build());
        }
        return result;
    }

    private boolean isAnnotated(Field field) {
        return AnnotationUtil.getAnnotationsOfType(field, Autowired.class).size() > 0;
    }
}

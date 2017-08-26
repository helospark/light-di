package com.helospark.lightdi.dependencywire;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.field.FieldDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class FieldWireSupport {
    private FindInDependencySupport findInDependencySupport;
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public FieldWireSupport(FindInDependencySupport findInDependencySupport,
            PropertyDescriptorFactory propertyDescriptorFactory) {
        this.findInDependencySupport = findInDependencySupport;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
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
            Class<?> fieldType = field.getType();
            DependencyDescriptorQuery query = DependencyDescriptorQuery
                    .builder()
                    .withClazz(fieldType)
                    .build();
            DependencyDescriptor dependencyDescriptor = findInDependencySupport.findOrThrow(dependencyDescriptors,
                    query);
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withFieldName(field.getName())
                    .withInjectionDescriptor(dependencyDescriptor)
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
            result.add(FieldDescriptor.builder()
                    .withField(field)
                    .withFieldName(field.getName())
                    .withInjectionDescriptor(propertyDescriptorFactory.buildPropertyDescriptor(field))
                    .build());
        }
        return result;
    }

    private boolean isAnnotated(Field field) {
        return field.getAnnotationsByType(Autowired.class).length > 0;
    }
}

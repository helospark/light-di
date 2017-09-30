package com.helospark.lightdi.dependencywire;

import static com.helospark.lightdi.util.AnnotationUtil.getSingleAnnotationOfType;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;

public class PropertyDescriptorFactory {
    public PropertyDescritor buildPropertyDescriptor(AnnotatedElement parameter, Class<?> type) {
        Value valueAnnotation = getSingleAnnotationOfType(parameter, Value.class);
        String value = valueAnnotation.value();
        boolean required = valueAnnotation.required();
        return new PropertyDescritor(type, value, required);
    }

    public InjectionDescriptor buildPropertyDescriptor(Parameter parameter) {
        return buildPropertyDescriptor(parameter, parameter.getType());
    }

    public InjectionDescriptor buildPropertyDescriptor(Field field) {
        return buildPropertyDescriptor(field, field.getType());
    }

    public InjectionDescriptor buildPropertyDescriptor(Method method) {
        return buildPropertyDescriptor(method, method.getParameterTypes()[0]);
    }
}

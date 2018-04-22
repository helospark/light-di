package com.helospark.lightdi.dependencywire;

import static com.helospark.lightdi.util.AnnotationUtil.getSingleAnnotationOfType;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.GenericClass;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;
import com.helospark.lightdi.util.ReflectionUtil;

public class PropertyDescriptorFactory {
    public PropertyDescritor buildPropertyDescriptor(AnnotatedElement parameter, Class<?> type, Optional<Class<?>> firstGenericType) {
        Value valueAnnotation = getSingleAnnotationOfType(parameter, Value.class);
        String value = valueAnnotation.value();
        boolean required = valueAnnotation.required();
        GenericClass genericClass = new GenericClass(type, firstGenericType);
        return new PropertyDescritor(genericClass, value, required);
    }

    public InjectionDescriptor buildPropertyDescriptor(Parameter parameter) {
        return buildPropertyDescriptor(parameter, parameter.getType(), ReflectionUtil.extractFirstGenericType(parameter));
    }

    public InjectionDescriptor buildPropertyDescriptor(Field field) {
        return buildPropertyDescriptor(field, field.getType(), ReflectionUtil.extractFirstGenericType(field));
    }

    public InjectionDescriptor buildPropertyDescriptor(Method method) {
        return buildPropertyDescriptor(method, method.getParameterTypes()[0],
                ReflectionUtil.extractGenericTypeFromType(method.getGenericParameterTypes()[0]));
    }
}

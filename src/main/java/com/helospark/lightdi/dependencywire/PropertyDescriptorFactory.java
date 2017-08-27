package com.helospark.lightdi.dependencywire;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;

public class PropertyDescriptorFactory {
    public PropertyDescritor buildPropertyDescriptor(Parameter parameter) {
        Value valueAnnotation = parameter.getAnnotation(Value.class);
        String value = valueAnnotation.value();
        return new PropertyDescritor(parameter.getType(), value);
    }

    public InjectionDescriptor buildPropertyDescriptor(Field field) {
        Value valueAnnotation = field.getAnnotation(Value.class);
        String value = valueAnnotation.value();
        return new PropertyDescritor(field.getType(), value);
    }

    public InjectionDescriptor buildPropertyDescriptor(Method method) {
        Value valueAnnotation = method.getAnnotation(Value.class);
        String value = valueAnnotation.value();
        return new PropertyDescritor(method.getParameterTypes()[0], value);
    }
}

package com.helospark.lightdi.dependencywire;

import static com.helospark.lightdi.annotation.Value.REQUIRED_ATTRIBUTE_NAME;
import static com.helospark.lightdi.annotation.Value.VALUE_ATTRIBUTE_NAME;
import static com.helospark.lightdi.util.AnnotationUtil.getSingleAnnotationOfType;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Optional;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.descriptor.GenericClass;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;
import com.helospark.lightdi.util.LightDiAnnotation;
import com.helospark.lightdi.util.ReflectionUtil;

public class PropertyDescriptorFactory {
    public PropertyDescritor buildPropertyDescriptor(AnnotatedElement parameter, Class<?> type, Optional<Class<?>> firstGenericType) {
        LightDiAnnotation valueAnnotation = getSingleAnnotationOfType(parameter, Value.class);
        String value = valueAnnotation.getAttributeAs(VALUE_ATTRIBUTE_NAME, String.class);
        boolean required = valueAnnotation.getAttributeAs(REQUIRED_ATTRIBUTE_NAME, Boolean.class);
        GenericClass genericClass = new GenericClass(type, firstGenericType);
        return new PropertyDescritor(genericClass, value, required);
    }

    public InjectionDescriptor buildPropertyDescriptor(Method method) {
        return buildPropertyDescriptor(method, method.getParameterTypes()[0],
                ReflectionUtil.extractGenericTypeFromType(method.getGenericParameterTypes()[0]));
    }
}

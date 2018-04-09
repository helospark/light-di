package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.helospark.lightdi.dependencywire.chain.support.domain.CompatibleAnnotatedElement;
import com.helospark.lightdi.dependencywire.chain.support.domain.CompatibleParameter;

public class CompatibleParameterFactory {

    public CompatibleParameter createParameter(Constructor<?> constructorToUse, int index) {
        Annotation[] annotatedParameterTypes = constructorToUse.getParameterAnnotations()[index];
        Class<?> parameterType = constructorToUse.getParameterTypes()[index];
        Type genericType = constructorToUse.getGenericParameterTypes()[index];
        return CompatibleParameter.builder()
                .withAnnotatedParameterTypes(new CompatibleAnnotatedElement(annotatedParameterTypes))
                .withGenericType(genericType)
                .withParameterType(parameterType)
                .build();
    }

    // Note that the method body is exactly the same as the above, and there is an Executable interface
    // do NOT use it, so this class stays compatible with Java 7 (Android).
    public CompatibleParameter createParameter(Method method, int index) {
        Annotation[] annotatedParameterTypes = method.getParameterAnnotations()[index];
        Class<?> parameterType = method.getParameterTypes()[index];
        Type genericType = method.getGenericParameterTypes()[index];
        return CompatibleParameter.builder()
                .withAnnotatedParameterTypes(new CompatibleAnnotatedElement(annotatedParameterTypes))
                .withGenericType(genericType)
                .withParameterType(parameterType)
                .build();
    }

}

package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;

import com.helospark.lightdi.annotation.Scope;
import com.helospark.lightdi.constants.LightDiConstants;

public class QualifierExtractor {

    public static String extractScope(Class<?> clazz) {
        if (hasAnnotation(clazz, Scope.class)) {
            Scope scope = AnnotationUtil.getSingleAnnotationOfType(clazz, Scope.class);
            return scope.value();
        }
        return LightDiConstants.SCOPE_SINGLETON;
    }

    public static String extractScope(Method method) {
        if (hasAnnotation(method, Scope.class)) {
            Scope scope = AnnotationUtil.getSingleAnnotationOfType(method, Scope.class);
            return scope.value();
        }
        return LightDiConstants.SCOPE_SINGLETON;
    }
}

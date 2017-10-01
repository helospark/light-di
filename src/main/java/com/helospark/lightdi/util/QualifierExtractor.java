package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Scope;
import com.helospark.lightdi.constants.LightDiConstants;

public class QualifierExtractor {

    public static String extractScope(AnnotatedElement clazz) {
        if (hasAnnotation(clazz, Scope.class)) {
            Scope scope = AnnotationUtil.getSingleAnnotationOfType(clazz, Scope.class);
            return scope.value();
        }
        return LightDiConstants.SCOPE_SINGLETON;
    }

}

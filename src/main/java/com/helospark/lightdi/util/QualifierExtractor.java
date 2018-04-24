package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Scope;
import com.helospark.lightdi.constants.LightDiConstants;

public class QualifierExtractor {

    public static String extractScope(AnnotatedElement clazz) {
        if (hasAnnotation(clazz, Scope.class)) {
            return AnnotationUtil.getSingleAnnotationOfType(clazz, Scope.class).getAttributeAs(Scope.SCOPE_ATTRIBUTE_NAME, String.class);
        }
        return LightDiConstants.SCOPE_SINGLETON;
    }

}

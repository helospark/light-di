package com.helospark.lightdi.util;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Primary;

public class IsPrimaryExtractor {

    public static boolean isPrimary(AnnotatedElement annotatedElement) {
        return AnnotationUtil.hasAnnotation(annotatedElement, Primary.class);
    }

}

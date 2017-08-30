package com.helospark.lightdi.annotation;

import java.lang.reflect.Method;

import com.helospark.lightdi.util.AnnotationUtil;

public class IsPrimaryExtractor {

    public static boolean isPrimary(Class<?> clazz) {
        return AnnotationUtil.hasAnnotation(clazz, Primary.class);
    }

    public static boolean isPrimary(Method method) {
        return AnnotationUtil.hasAnnotation(method, Primary.class);
    }

}

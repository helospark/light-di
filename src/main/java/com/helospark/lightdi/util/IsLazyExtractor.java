package com.helospark.lightdi.util;

import java.lang.reflect.Method;

import com.helospark.lightdi.annotation.Eager;
import com.helospark.lightdi.annotation.Lazy;
import com.helospark.lightdi.exception.IllegalConfigurationException;

public class IsLazyExtractor {

    public static boolean isLazy(Class<?> clazz) {
        boolean hasEagerAnnotation = AnnotationUtil.hasAnnotation(clazz, Eager.class);
        boolean hasLazyAnnotation = AnnotationUtil.hasAnnotation(clazz, Lazy.class);
        return isLazy(hasEagerAnnotation, hasLazyAnnotation);
    }

    public static boolean isLazy(Method method) {
        boolean hasEagerAnnotation = AnnotationUtil.hasAnnotation(method, Eager.class);
        boolean hasLazyAnnotation = AnnotationUtil.hasAnnotation(method, Lazy.class);
        return isLazy(hasEagerAnnotation, hasLazyAnnotation);
    }

    private static boolean isLazy(boolean hasEagerAnnotation, boolean hasLazyAnnotation) {
        if (hasEagerAnnotation && hasLazyAnnotation) {
            throw new IllegalConfigurationException("Both lazy and eager annotation is present");
        }
        if (hasEagerAnnotation) {
            return false;
        }
        if (hasLazyAnnotation) {
            return true;
        }
        return true;
    }

}

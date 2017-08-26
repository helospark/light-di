package com.helospark.lightdi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationUtil {

    public static boolean hasAnnotation(Class<?> className, Class<? extends Annotation> annotationToFind) {
        return Arrays.stream(className.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(annotationToFind))
                .findFirst()
                .isPresent();
    }

    public static boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotation) {
        return parameter.getAnnotation(annotation) != null;
    }

    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationToFind) {
        return field.getAnnotationsByType(annotationToFind).length > 0;
    }

    public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationToFind) {
        return method.getAnnotationsByType(annotationToFind).length > 0;
    }

    public static List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation>... annotations) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> doesMethodContainAnyAnnotationOf(method, annotations))
                .collect(Collectors.toList());
    }

    public static boolean doesMethodContainAnyAnnotationOf(Method method, Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> hasAnnotation(method, annotation))
                .findFirst()
                .isPresent();
    }
}

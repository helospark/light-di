package com.helospark.lightdi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationUtil {

    public static <T extends Annotation> Set<T> getAnnotationsOfType(AnnotatedElement parameter, Class<T> annotationToFind) {
        Set<Annotation> annotations = getAllMergedAnnotations(parameter);
        return annotations.stream()
                .filter(annotation -> annotation.annotationType().equals(annotationToFind))
                .map(annotation -> (T) annotation)
                .collect(Collectors.toSet());
    }

    public static <T extends Annotation> T getSingleAnnotationOfType(AnnotatedElement parameter, Class<T> annotationToFind) {
        Set<T> annotations = getAnnotationsOfType(parameter, annotationToFind);
        if (annotations.size() != 1) {
            throw new IllegalArgumentException(parameter + " does not contain exactly one " + annotationToFind);
        }
        return annotations.stream().findFirst().get();
    }

    public static boolean hasAnnotation(AnnotatedElement parameter, Class<? extends Annotation> annotation) {
        return getAnnotationsOfType(parameter, annotation).size() > 0;
    }

    public static List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation>... annotations) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> doesElementContainAnyAnnotationOf(method, annotations))
                .collect(Collectors.toList());
    }

    public static boolean doesElementContainAnyAnnotationOf(AnnotatedElement method, Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> hasAnnotation(method, annotation))
                .findFirst()
                .isPresent();
    }

    /**
     * Gets all annotation on the given element, including annotations on annotations.
     * It also contains meta annotations (if present) such as Documented or Retention, but deduplicated, if mulitple annotations have it.
     * @param parameter to extract annotations from
     * @return all annotations
     */
    public static Set<Annotation> getAllMergedAnnotations(AnnotatedElement parameter) {
        Set<Annotation> result = new HashSet<>();
        return recursivelyMergeAllAnnotationsInternal(parameter, result);
    }

    private static Set<Annotation> recursivelyMergeAllAnnotationsInternal(AnnotatedElement parameter, Set<Annotation> result) {
        for (Annotation annotation : parameter.getAnnotations()) {
            // meta annotations may contain themself, see @Retention, avoid
            // recursing if it already containd it.
            if (!result.contains(annotation)) {
                result.add(annotation);
                result.addAll(recursivelyMergeAllAnnotationsInternal(annotation.annotationType(), result));
            }
        }
        return result;
    }
}

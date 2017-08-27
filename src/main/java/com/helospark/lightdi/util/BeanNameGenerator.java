package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.StereotypeAnnotationValueExtractor.getStereotypeAnnotationValue;

import java.lang.reflect.Method;

import com.helospark.lightdi.annotation.Bean;

public class BeanNameGenerator {

    public static String generateBeanName(String qualifier, Class<?> clazz) {
        if (!qualifier.isEmpty()) {
            return qualifier;
        } else {
            String className = clazz.getSimpleName();
            char lowercaseFirstLetter = Character.toLowerCase(className.charAt(0));
            return String.valueOf(lowercaseFirstLetter) + className.substring(1);
        }
    }

    public static String createBeanNameForStereotypeAnnotatedClass(Class<?> clazz) {
        String annotationValue = getStereotypeAnnotationValue(clazz);
        return generateBeanName(annotationValue, clazz);
    }

    public static String createQualifierForMethodAnnotatedWithBean(Method method) {
        String annotationValue = method.getAnnotationsByType(Bean.class)[0].value();
        return annotationValue.isEmpty() ? method.getName() : annotationValue;
    }
}

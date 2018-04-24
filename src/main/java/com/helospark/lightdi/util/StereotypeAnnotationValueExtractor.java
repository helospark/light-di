package com.helospark.lightdi.util;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Service;
import com.helospark.lightdi.exception.IllegalConfigurationException;

public class StereotypeAnnotationValueExtractor {
    public static String getStereotypeAnnotationValue(Class<?> clazz) {
        Optional<String> component = getAtMaxOneAnnotation(clazz, Component.class, Component.NAME_ATTRIBUTE_NAME);
        if (component.isPresent()) {
            return component.get();
        }
        Optional<String> service = getAtMaxOneAnnotation(clazz, Service.class, Service.NAME_ATTRIBUTE_NAME);
        if (service.isPresent()) {
            return service.get();
        }
        Optional<String> configuration = getAtMaxOneAnnotation(clazz, Configuration.class, Configuration.NAME_ATTRIBUTE_NAME);
        if (configuration.isPresent()) {
            return configuration.get();
        }
        return "";
    }

    private static Optional<String> getAtMaxOneAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType, String attributeName) {
        Set<LightDiAnnotation> component = AnnotationUtil.getAnnotationsOfType(clazz, annotationType);
        if (component.size() > 1) {
            throw new IllegalConfigurationException("There can be at max one annotation of type " + annotationType + "on " + clazz);
        }
        if (component.size() > 0) {
            return Optional.of(component.stream().findFirst().get().getAttributeAs(attributeName, String.class));
        } else {
            return Optional.empty();
        }
    }
}

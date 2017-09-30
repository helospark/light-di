package com.helospark.lightdi.util;

import java.util.Set;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Service;

public class StereotypeAnnotationValueExtractor {
    public static String getStereotypeAnnotationValue(Class<?> clazz) {
        Set<Component> component = AnnotationUtil.getAnnotationsOfType(clazz, Component.class);
        if (component.size() > 0) {
            return component.stream().findFirst().get().value();
        }
        Set<Service> service = AnnotationUtil.getAnnotationsOfType(clazz, Service.class);
        if (service.size() > 0) {
            return service.stream().findFirst().get().value();
        }
        Set<Configuration> configuration = AnnotationUtil.getAnnotationsOfType(clazz, Configuration.class);
        if (configuration.size() > 0) {
            return configuration.stream().findFirst().get().value();
        }
        return "";
    }
}

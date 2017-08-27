package com.helospark.lightdi.util;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Service;

public class StereotypeAnnotationValueExtractor {
    public static String getStereotypeAnnotationValue(Class<?> clazz) {
        Component[] component = clazz.getAnnotationsByType(Component.class);
        if (component.length > 0) {
            return component[0].value();
        }
        Service[] service = clazz.getAnnotationsByType(Service.class);
        if (service.length > 0) {
            return service[0].value();
        }
        Configuration[] configuration = clazz.getAnnotationsByType(Configuration.class);
        if (configuration.length > 0) {
            return configuration[0].value();
        }
        return "";
    }
}

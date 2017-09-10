package com.helospark.lightdi.util;

import java.lang.reflect.Field;

public class ReflectionUtil {
    public static Class<?> createClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void injectToField(Field field, Object instance, Object valueToInject) {
        try {
            field.setAccessible(true);
            field.set(instance, valueToInject);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject value to field", e);
        }
    }

}

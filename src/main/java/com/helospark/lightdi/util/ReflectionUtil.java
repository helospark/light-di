package com.helospark.lightdi.util;

public class ReflectionUtil {
    public static Class<?> createClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

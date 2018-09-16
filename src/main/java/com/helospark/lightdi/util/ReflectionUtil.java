package com.helospark.lightdi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ReflectionUtil {
    private static Set<Method> objectMethods = null;

    static {
        objectMethods = new HashSet<>(Arrays.asList(Object.class.getMethods()));
    }

    public static Class<?> createClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<Method> getNonObjectMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> !isInJavaLangObject(method));
    }

    private static boolean isInJavaLangObject(Method method) {
        return objectMethods.contains(method);
    }

    public static void injectToField(Field field, Object instance, Object valueToInject) {
        try {
            field.setAccessible(true);
            field.set(instance, valueToInject);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject value to field", e);
        }
    }

    public static Optional<Class<?>> extractFirstGenericType(Parameter parameter) {
        return extractGenericTypeFromType(parameter.getParameterizedType());
    }

    public static Optional<Class<?>> extractFirstGenericType(Field field) {
        return extractGenericTypeFromType(field.getGenericType());
    }

    public static Optional<Class<?>> extractGenericTypeFromType(Type genericType) {
        Optional<Type> extractedType = Optional.of(genericType)
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .map(type -> type.getActualTypeArguments())
                .map(genericTypes -> Arrays.stream(genericTypes))
                .orElse(Stream.empty())
                .findFirst();

        if (extractedType.isPresent()) {
            Type type = extractedType.get();

            if (type instanceof Class) {
                return Optional.of((Class<?>) type);
            } else if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType instanceof Class<?>) {
                    return Optional.of((Class<?>) rawType);
                }
            }
            return Optional.empty();
        }

        return Optional.empty();
    }
}

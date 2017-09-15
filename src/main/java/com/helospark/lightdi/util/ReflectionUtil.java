package com.helospark.lightdi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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

    public static Optional<Class<?>> extractFirstGenericType(Parameter parameter) {
        Optional<Type> extractedType = Optional.ofNullable(parameter)
                .map(param -> param.getParameterizedType())
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

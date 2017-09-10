package com.helospark.lightdi.properties;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.LightDiContext;

public class ValueResolver {
    private static final String VALUE_ARRAY_SEPARATOR = ",";
    private PropertyStringResolver propertyStringResolver;

    public ValueResolver(PropertyStringResolver propertyStringResolver) {
        this.propertyStringResolver = propertyStringResolver;
    }

    public <T> T resolve(String value, Class<T> type, LightDiContext context, Collection<PropertySourceHolder> properties) {
        String resolvedValue = propertyStringResolver.resolve(value, properties);
        if (type.isArray()) {
            Class<?> elementType = type.getComponentType();
            PropertyConverter<?> converter = findConverterForType(elementType, context);
            Object[] arrayResult = Arrays.stream(resolvedValue.split(VALUE_ARRAY_SEPARATOR))
                    .map(v -> converter.convert(v))
                    .collect(Collectors.toList())
                    .toArray();
            return (T) Arrays.copyOf(arrayResult, arrayResult.length, (Class<T[]>) type);
        } else {
            PropertyConverter<T> converter = findConverterForType(type, context);
            return converter.convert(resolvedValue);
        }

    }

    private <T> PropertyConverter<T> findConverterForType(Class<T> type, LightDiContext context) {
        List<PropertyConverter> converters = context.getListOfBeans(PropertyConverter.class);
        Stream<PropertyConverter<T>> possibleConverters = converters.stream()
                .filter(converter -> canHandle(converter, type))
                .map(converter -> (PropertyConverter<T>) converter);

        return possibleConverters.findFirst()
                .orElseThrow(() -> new RuntimeException("No converter found for type " + type.getName()));
    }

    private boolean canHandle(PropertyConverter converter, Class<?> type) {
        Method method;
        try {
            method = converter.getClass().getMethod("convert", String.class);
            return type.isAssignableFrom(method.getReturnType());
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}

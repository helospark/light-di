package com.helospark.lightdi.properties;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.GenericClass;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.CollectionFactory;

@SuppressWarnings("unchecked")
public class ValueResolver {
    private static final String VALUE_ARRAY_SEPARATOR = ",";
    private PropertyStringResolver propertyStringResolver;
    private AssignablePredicate assignablePredicate;
    private CollectionFactory collectionFactory;

    public ValueResolver(PropertyStringResolver propertyStringResolver, AssignablePredicate assignablePredicate,
            CollectionFactory collectionFactory) {
        this.propertyStringResolver = propertyStringResolver;
        this.assignablePredicate = assignablePredicate;
        this.collectionFactory = collectionFactory;
    }

    public <T> T resolve(String value, GenericClass genericType, LightDiContext context, Collection<PropertySourceHolder> properties) {
        String resolvedValue = propertyStringResolver.resolve(value, properties);
        Class<T> type = (Class<T>) genericType.getType();
        if (type.equals(String.class)) {
            return (T) resolvedValue;
        }
        if (type.isArray()) {
            Class<?> elementType = type.getComponentType();
            List<Object> listResult = convertListOfElements(context, resolvedValue, elementType);
            Object[] arrayResult = listResult.toArray();
            return (T) Arrays.copyOf(arrayResult, arrayResult.length, (Class<T[]>) type);
        } else if (Collection.class.isAssignableFrom(type)) {
            return (T) handleCollection(genericType, context, resolvedValue);
        } else {
            PropertyConverter<T> converter = findConverterForType(type, context);
            return converter.convert(resolvedValue);
        }

    }

    private <T> Collection<T> handleCollection(GenericClass genericType, LightDiContext context, String resolvedValue) {
        Class<T> elementType = (Class<T>) genericType.getFirstGenericType()
                .orElseThrow(() -> new IllegalConfigurationException("Value injection is requested, but no generic information is present"));
        List<T> listResult = (List<T>) convertListOfElements(context, resolvedValue, elementType);
        return convertListToCollection(listResult, (Class<? extends Collection<T>>) genericType.getType());
    }

    private <T> Collection<T> convertListToCollection(List<T> listResult, Class<? extends Collection<T>> type) {
        Collection<T> collection = (Collection<T>) collectionFactory.createCollectionFor(type);
        collection.addAll(listResult);
        return collection;
    }

    private List<Object> convertListOfElements(LightDiContext context, String resolvedValue, Class<?> elementType) {
        PropertyConverter<?> converter = findConverterForType(elementType, context);
        return Arrays.stream(resolvedValue.split(VALUE_ARRAY_SEPARATOR))
                .map(v -> removeWhitespacesAround(v))
                .map(v -> converter.convert(v))
                .collect(Collectors.toList());
    }

    private String removeWhitespacesAround(String v) {
        return v.trim();
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
            return assignablePredicate.canBeAssigned(type, method.getReturnType());
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}

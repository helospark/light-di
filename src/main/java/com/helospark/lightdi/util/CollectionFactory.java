package com.helospark.lightdi.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;

import com.helospark.lightdi.exception.IllegalConfigurationException;

public class CollectionFactory {
    private Map<Class<? extends Collection>, Supplier<Collection<?>>> defaultCollections;

    public CollectionFactory() {
        defaultCollections = new HashMap<>();
        defaultCollections.put(List.class, () -> new ArrayList<>());
        defaultCollections.put(Set.class, () -> new HashSet<>());
        defaultCollections.put(Queue.class, () -> new ArrayDeque<>());
        defaultCollections.put(Collection.class, () -> new ArrayList<>());
    }

    public Collection<?> createCollectionFor(Class<? extends Collection<?>> clazz) {
        for (Map.Entry<Class<? extends Collection>, Supplier<Collection<?>>> entry : defaultCollections.entrySet()) {
            if (entry.getKey().equals(clazz)) {
                return entry.getValue().get();
            }
        }
        return tryInstantiatingViaReflection(clazz);
    }

    private Collection<?> tryInstantiatingViaReflection(Class<? extends Collection<?>> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalConfigurationException("Collection type is not supported for injecting beans " + clazz, e);
        }
    }
}

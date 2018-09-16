package com.helospark.lightdi.internal;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import com.helospark.lightdi.LightDiContextConfiguration;

/**
 * Internal DI to bootstrap LightDi.
 * <p>
 * You should not depend on any of this in your application.
 * 
 * @author helospark
 */
public class DefaultInternalDi implements InternalDi {
    private static final int APPROXIMATE_NUMBER_OF_DEPENDENCIES = 100;

    private List<Object> diContainer = new ArrayList<>(APPROXIMATE_NUMBER_OF_DEPENDENCIES);

    @Override
    public void initialize(LightDiContextConfiguration lightDiContextConfiguration) {
    }

    @Override
    public void addDependency(Object dependency) {
        diContainer.add(dependency);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getDependency(Class<T> clazz) {
        for (int i = diContainer.size() - 1; i >= 0; --i) {
            Object value = diContainer.get(i);
            if (clazz.isAssignableFrom(value.getClass())) {
                return (T) value;
            }
        }
        throw new RuntimeException("Unable to initialize " + clazz.getName() + " not found");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getDependencyList(Class<T> classToFind) {
        List<Object> result = new ArrayList<>();
        for (Object o : diContainer) {
            if (classToFind.isAssignableFrom(o.getClass())) {
                result.add(o);
            }
        }
        return (List<T>) result;
    }

    @Override
    public <T> List<T> getDependencyList(T... objects) {
        return asList(objects);
    }

}

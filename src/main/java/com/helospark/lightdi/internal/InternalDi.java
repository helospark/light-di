package com.helospark.lightdi.internal;

import java.util.List;

import com.helospark.lightdi.LightDiContextConfiguration;

/**
 * Internal DI to bootstrap LightDi.
 * <p>
 * You should not depend on any of this in your application unless you are
 * trying to add a plugin.
 * 
 * @author helospark
 */
public interface InternalDi {

    void initialize(LightDiContextConfiguration lightDiContextConfiguration);

    void addDependency(Object dependency);

    <T> T getDependency(Class<T> clazz);

    <T> List<T> getDependencyList(Class<T> classToFind);

    <T> List<T> getDependencyList(T... objects);

}
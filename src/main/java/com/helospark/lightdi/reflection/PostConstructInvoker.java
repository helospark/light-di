package com.helospark.lightdi.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class PostConstructInvoker {

    public void invokePostConstructMethods(DependencyDescriptor dependencyToCreate, Object createdBean) {
        dependencyToCreate.getPostConstructMethods()
                .stream()
                .forEach(method -> callPostConstructMethod(createdBean, method));
    }

    private void callPostConstructMethod(Object result, Method method) {
        try {
            method.invoke(result);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

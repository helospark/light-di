package com.helospark.lightdi.reflection;

import java.lang.reflect.Field;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;

public class FieldSetInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public FieldSetInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public void setFieldValues(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate,
            Object result) {
        dependencyToCreate.getFieldDescriptor()
                .stream()
                .forEach(setter -> setFieldValue(lightDiContext, setter, result));
    }

    private void setFieldValue(LightDiContext lightDiContext, FieldDescriptor field, Object result) {
        try {
            InjectionDescriptor injectionDescriptor = field.getInjectionDescriptor();
            Object objectToSet = dependencyObjectResolverHandler.resolve(lightDiContext, injectionDescriptor);
            if (objectToSet != null) {
                Field reflectField = field.getField();
                reflectField.setAccessible(true);
                reflectField.set(result, objectToSet);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to call setter " + field, e);
        }
    }
}

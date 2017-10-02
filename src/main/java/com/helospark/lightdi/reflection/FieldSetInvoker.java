package com.helospark.lightdi.reflection;

import java.lang.reflect.Field;
import java.util.Optional;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.specialinject.InjectionPoint;

public class FieldSetInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public FieldSetInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public void setFieldValues(LightDiContext lightDiContext, StereotypeDependencyDescriptor dependencyToCreate,
            Object result) {
        dependencyToCreate.getFieldDescriptor()
                .stream()
                .forEach(setter -> setFieldValue(lightDiContext, setter, result, dependencyToCreate));
    }

    private void setFieldValue(LightDiContext lightDiContext, FieldDescriptor field, Object result,
            StereotypeDependencyDescriptor dependencyToCreate) {
        try {
            InjectionDescriptor injectionDescriptor = field.getInjectionDescriptor();

            InjectionPoint injectionPoint = InjectionPoint.builder()
                    .withFieldDescriptor(Optional.of(field))
                    .withInjectionDescriptor(injectionDescriptor)
                    .withDependencyDescriptor(dependencyToCreate)
                    .build();

            Object objectToSet = dependencyObjectResolverHandler.resolve(lightDiContext, injectionDescriptor, injectionPoint);
            Field reflectField = field.getField();
            reflectField.setAccessible(true);
            reflectField.set(result, objectToSet);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to call setter " + field, e);
        }
    }
}

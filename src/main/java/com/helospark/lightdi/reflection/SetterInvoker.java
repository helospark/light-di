package com.helospark.lightdi.reflection;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;

public class SetterInvoker {
    private MethodInvoker methodInvoker;

    public SetterInvoker(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    public void invokeSetters(LightDiContext lightDiContext, StereotypeDependencyDescriptor dependencyToCreate,
            Object result) {
        dependencyToCreate.getSetterDescriptor()
                .stream()
                .forEach(setter -> methodInvoker.invokeMethod(lightDiContext, setter, result));

    }
}

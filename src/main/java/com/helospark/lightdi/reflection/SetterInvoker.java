package com.helospark.lightdi.reflection;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.setter.SetterDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;

public class SetterInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public SetterInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public void invokeSetters(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate, Object result) {
        dependencyToCreate.getSetterDescriptor()
                .stream()
                .forEach(setter -> invokeSetter(lightDiContext, setter, result));
    }

    private void invokeSetter(LightDiContext lightDiContext, SetterDescriptor setter, Object result) {
        try {
            InjectionDescriptor injectionDescriptor = setter.getInjectionDescriptor();
            Object objectToSet = dependencyObjectResolverHandler.resolve(lightDiContext, injectionDescriptor);
            setter.getMethod().invoke(result, objectToSet);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to call setter " + setter, e);
        }
    }

}

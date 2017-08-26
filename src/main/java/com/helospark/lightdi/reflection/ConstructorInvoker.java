package com.helospark.lightdi.reflection;

import java.lang.reflect.Constructor;
import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.constructor.ConstructorDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;

public class ConstructorInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public ConstructorInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public Object invokeConstructor(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate)
            throws Exception {
        List<ConstructorDescriptor> constructorDescriptors = dependencyToCreate.getConstructorDescriptors();
        Object[] params = new Object[constructorDescriptors.size()];

        for (int i = 0; i < constructorDescriptors.size(); ++i) {
            ConstructorDescriptor descriptor = constructorDescriptors.get(i);
            InjectionDescriptor injectionDescriptor = descriptor.getDependencyDescriptor();

            params[descriptor.getIndex()] = dependencyObjectResolverHandler.resolve(lightDiContext,
                    injectionDescriptor);
        }
        Constructor<?> method = dependencyToCreate.getClazz().getConstructors()[0]; // bug

        return method.newInstance(params);
    }

}

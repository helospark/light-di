package com.helospark.lightdi.reflection;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;

public class ConstructorInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public ConstructorInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public Object invokeConstructor(LightDiContext lightDiContext, StereotypeDependencyDescriptor dependencyToCreate)
            throws Exception {
        List<ConstructorDescriptor> constructorDescriptors = dependencyToCreate.getConstructorDescriptor();
        Object[] params = new Object[constructorDescriptors.size()];

        for (int i = 0; i < constructorDescriptors.size(); ++i) {
            ConstructorDescriptor descriptor = constructorDescriptors.get(i);
            InjectionDescriptor injectionDescriptor = descriptor.getDependencyDescriptor();

            params[descriptor.getIndex()] = dependencyObjectResolverHandler.resolve(lightDiContext,
                    injectionDescriptor);
        }
        Optional<Constructor<?>> method = getConstructorToUse(constructorDescriptors, dependencyToCreate);

        if (method.isPresent()) {
            return method.get().newInstance(params);
        } else {
            return dependencyToCreate.getClazz().newInstance(); // without constructor
        }
    }

    private Optional<Constructor<?>> getConstructorToUse(List<ConstructorDescriptor> constructorDescriptors,
            StereotypeDependencyDescriptor dependencyToCreate) {
        if (constructorDescriptors.size() > 0) {
            return Optional.of(constructorDescriptors.get(0).getConstructor()); // all element has the same constructor
        } else {
            return Optional.empty();
        }
    }

}

package com.helospark.lightdi.reflection.chain;

import static com.helospark.lightdi.reflection.DependencyGetter.getDependency;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class DependentObjectResolverChainItem implements DependencyObjectResolverChainItem {

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        DependencyDescriptor dependencyDescriptor = (DependencyDescriptor) injectionDescriptor;
        return getDependency(context, dependencyDescriptor);
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof DependencyDescriptor;
    }

}

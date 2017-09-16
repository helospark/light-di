package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.NullInjectDescriptor;

public class NullInjectResolverChainItem implements DependencyObjectResolverChainItem {

    /**
     * Always return null for NullInjectDescriptor.
     */
    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        return null;
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof NullInjectDescriptor;
    }

}

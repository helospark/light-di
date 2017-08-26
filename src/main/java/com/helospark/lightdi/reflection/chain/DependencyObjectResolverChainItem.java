package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public interface DependencyObjectResolverChainItem {
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor);

    public boolean canHandle(InjectionDescriptor injectionDescriptor);
}

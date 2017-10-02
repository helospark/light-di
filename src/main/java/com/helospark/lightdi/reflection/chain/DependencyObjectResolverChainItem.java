package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.specialinject.InjectionPoint;

public interface DependencyObjectResolverChainItem {
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor, InjectionPoint injectionPoint);

    public boolean canHandle(InjectionDescriptor injectionDescriptor);
}

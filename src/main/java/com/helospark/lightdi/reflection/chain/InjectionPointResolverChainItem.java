package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.SpecialValueTypeInjectionDescriptor;
import com.helospark.lightdi.specialinject.InjectionPoint;

public class InjectionPointResolverChainItem implements DependencyObjectResolverChainItem {

    // Note: Passing the entire InjectionPoint to the chain at first seem to defeat the purpose of the interface of this chain
    // on the other hand, I can see usefulness of InjectionPoint in the chain in the future.
    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor, InjectionPoint injectionPoint) {
        return injectionPoint;
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        if (injectionDescriptor instanceof SpecialValueTypeInjectionDescriptor) {
            return ((SpecialValueTypeInjectionDescriptor) injectionDescriptor).getClazz().equals(InjectionPoint.class);
        } else {
            return false;
        }
    }

}

package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.SpecialValueDependencyDescriptor;

public class SpecialValueObjectResolverChainItem implements DependencyObjectResolverChainItem {

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        return ((SpecialValueDependencyDescriptor) injectionDescriptor).getSpecialValue();
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof SpecialValueDependencyDescriptor;
    }

}

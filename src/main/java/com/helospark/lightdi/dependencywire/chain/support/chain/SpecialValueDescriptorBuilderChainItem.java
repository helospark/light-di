package com.helospark.lightdi.dependencywire.chain.support.chain;

import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.SpecialValueDependencyDescriptor;

public class SpecialValueDescriptorBuilderChainItem implements InjectDescriptorBuilderChainItem {

    @Override
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request) {
        return SpecialValueDependencyDescriptor.builder()
                .withSpecialValue(request.getInjectionDescriptorToCreate())
                .build();
    }

    @Override
    public boolean doesSupport(InjectDescriptorBuilderRequest request) {
        return InjectionDescriptor.class.isAssignableFrom(request.getType());
    }

}

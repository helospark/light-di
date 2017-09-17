package com.helospark.lightdi.dependencywire.chain.support.chain;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public interface InjectDescriptorBuilderChainItem {
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request);

    public boolean doesSupport(InjectDescriptorBuilderRequest request);
}

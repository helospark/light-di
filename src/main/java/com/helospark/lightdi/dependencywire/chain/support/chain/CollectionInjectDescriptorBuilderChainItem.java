package com.helospark.lightdi.dependencywire.chain.support.chain;

import java.util.Collection;

import com.helospark.lightdi.dependencywire.FindInDependencySupport;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.ReflectionUtil;

public class CollectionInjectDescriptorBuilderChainItem implements InjectDescriptorBuilderChainItem {
    private FindInDependencySupport findInDependencySupport;

    public CollectionInjectDescriptorBuilderChainItem(FindInDependencySupport findInDependencySupport) {
        this.findInDependencySupport = findInDependencySupport;
    }

    @Override
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(ReflectionUtil.extractGenericTypeFromType(request.getGenericType())
                        .orElseThrow(() -> new IllegalConfigurationException("Collection without generic type")))
                .build();
        return findInDependencySupport.findListOrEmpty(request.getDependencyDescriptors(), query, (Class<? extends Collection<?>>) request.getType());
    }

    @Override
    public boolean doesSupport(InjectDescriptorBuilderRequest request) {
        return Collection.class.isAssignableFrom(request.getType());
    }
}

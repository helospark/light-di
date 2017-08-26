package com.helospark.lightdi.reflection.chain;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class DependencyObjectResolverHandler {
    private List<DependencyObjectResolverChainItem> resolverChainItems;

    public DependencyObjectResolverHandler(List<DependencyObjectResolverChainItem> resolverChainItems) {
        this.resolverChainItems = resolverChainItems;
    }

    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        return resolverChainItems.stream()
                .filter(chainItem -> chainItem.canHandle(injectionDescriptor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No chain item can handle the injection mode"))
                .resolve(context, injectionDescriptor);
    }
}

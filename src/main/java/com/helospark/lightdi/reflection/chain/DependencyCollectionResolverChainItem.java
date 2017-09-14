package com.helospark.lightdi.reflection.chain;

import java.util.Collection;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyCollectionDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.CollectionFactory;

public class DependencyCollectionResolverChainItem implements DependencyObjectResolverChainItem {
    private CollectionFactory collectionFactoryUtil;

    public DependencyCollectionResolverChainItem(CollectionFactory collectionFactoryUtil) {
        this.collectionFactoryUtil = collectionFactoryUtil;
    }

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        DependencyCollectionDescriptor collectionDescriptor = (DependencyCollectionDescriptor) injectionDescriptor;
        Class<? extends Collection<?>> collectionType = collectionDescriptor.getCollectionType();
        Collection<Object> collection = (Collection<Object>) collectionFactoryUtil.createCollectionFor(collectionType);

        collectionDescriptor.getDependencies()
                .stream()
                .map(descriptor -> context.getBean(descriptor))
                .forEach(element -> collection.add(element));

        return collection;
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof DependencyCollectionDescriptor;
    }

}

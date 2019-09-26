package com.helospark.lightdi.reflection.chain;

import java.util.Collection;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyCollectionDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
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

        for (DependencyDescriptor descriptor : collectionDescriptor.getDependencies()) {
            Object bean = context.getBean(descriptor);
            if (bean instanceof Collection) {
                collection.addAll((Collection) bean);
            } else {
                collection.add(bean);
            }
        }

        return collection;
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof DependencyCollectionDescriptor;
    }

}

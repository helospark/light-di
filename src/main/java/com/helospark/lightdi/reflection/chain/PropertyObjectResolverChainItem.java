package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;
import com.helospark.lightdi.properties.ValueResolver;

public class PropertyObjectResolverChainItem implements DependencyObjectResolverChainItem {

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        PropertyDescritor propertyDescriptor = ((PropertyDescritor) injectionDescriptor);
        ValueResolver valueResolver = context.getValueResolver();
        return valueResolver.resolve(propertyDescriptor.getValue(), propertyDescriptor.getClazz(), context);
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof PropertyDescritor;
    }

}

package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;
import com.helospark.lightdi.properties.Environment;

public class PropertyObjectResolverChainItem implements DependencyObjectResolverChainItem {

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
        PropertyDescritor propertyDescriptor = ((PropertyDescritor) injectionDescriptor);
        Environment environment = context.getEnvironment();
        return environment.resolve(propertyDescriptor.getValue(), propertyDescriptor.getClazz());
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof PropertyDescritor;
    }

}

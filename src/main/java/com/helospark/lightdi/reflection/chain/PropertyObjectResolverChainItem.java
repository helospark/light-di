package com.helospark.lightdi.reflection.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.property.PropertyDescritor;
import com.helospark.lightdi.properties.Environment;
import com.helospark.lightdi.specialinject.InjectionPoint;

public class PropertyObjectResolverChainItem implements DependencyObjectResolverChainItem {
    private static final String DEFAULT_PROPERTY_VALUE = "";

    @Override
    public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor, InjectionPoint injectionPoint) {
        PropertyDescritor propertyDescriptor = ((PropertyDescritor) injectionDescriptor);
        Environment environment = context.getEnvironment();
        try {
            Object resolved = environment.resolve(propertyDescriptor.getValue(), propertyDescriptor.getClazz());
            return resolved;
        } catch (IllegalArgumentException e) {
            if (propertyDescriptor.isRequired()) {
                throw new IllegalArgumentException("Cannot resolve " + propertyDescriptor.getValue(), e);
            } else {
                return DEFAULT_PROPERTY_VALUE;
            }
        }
    }

    @Override
    public boolean canHandle(InjectionDescriptor injectionDescriptor) {
        return injectionDescriptor instanceof PropertyDescritor;
    }

}

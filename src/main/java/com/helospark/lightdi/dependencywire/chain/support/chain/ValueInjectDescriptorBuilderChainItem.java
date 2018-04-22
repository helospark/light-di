package com.helospark.lightdi.dependencywire.chain.support.chain;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.dependencywire.PropertyDescriptorFactory;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.ReflectionUtil;

public class ValueInjectDescriptorBuilderChainItem implements InjectDescriptorBuilderChainItem {
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public ValueInjectDescriptorBuilderChainItem(PropertyDescriptorFactory propertyDescriptorFactory) {
        this.propertyDescriptorFactory = propertyDescriptorFactory;
    }

    @Override
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request) {
        return propertyDescriptorFactory.buildPropertyDescriptor(request.getParameter(), request.getType(),
                ReflectionUtil.extractGenericTypeFromType(request.getGenericType()));
    }

    @Override
    public boolean doesSupport(InjectDescriptorBuilderRequest request) {
        return AnnotationUtil.hasAnnotation(request.getParameter(), Value.class);
    }
}

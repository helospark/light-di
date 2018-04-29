package com.helospark.lightdi.it.specialvaluecontext;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

@Configuration
public class SpecialValueWithConstructorInjectConfiguration {
    private DependencyDescriptor descriptor;

    public SpecialValueWithConstructorInjectConfiguration(DependencyDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public DependencyDescriptor getDescriptor() {
        return descriptor;
    }

}

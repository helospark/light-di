package com.helospark.lightdi.it.specialvaluecontext;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

@Configuration
public class SpecialValueWithFieldInjectConfiguration {
    @Autowired
    private DependencyDescriptor descriptor;

    public DependencyDescriptor getDescriptor() {
        return descriptor;
    }

}

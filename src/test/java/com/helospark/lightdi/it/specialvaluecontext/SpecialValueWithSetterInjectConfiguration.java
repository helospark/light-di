package com.helospark.lightdi.it.specialvaluecontext;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

@Configuration
public class SpecialValueWithSetterInjectConfiguration {
    private DependencyDescriptor descriptor;

    @Autowired
    public void setDescriptor(DependencyDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public DependencyDescriptor getDescriptor() {
        return descriptor;
    }

}

package com.helospark.lightdi.it.specialvaluecontext;

import javax.annotation.PostConstruct;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

@Configuration
public class SpecialValueWithPostConstructInjectConfiguration {
    private DependencyDescriptor descriptor;

    @PostConstruct
    public void postConstruct(DependencyDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public DependencyDescriptor getDescriptor() {
        return descriptor;
    }

}

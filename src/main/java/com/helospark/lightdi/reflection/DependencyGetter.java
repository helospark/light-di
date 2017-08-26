package com.helospark.lightdi.reflection;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class DependencyGetter {

    public static Object getDependency(LightDiContext lightDiContext, DependencyDescriptor dependencyDescriptor) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(dependencyDescriptor.getClazz())
                .withQualifier(dependencyDescriptor.getQualifier())
                .build();
        return lightDiContext.getOrCreateBean(query);
    }
}

package com.helospark.lightdi.reflection;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface ObjectFromDescriptor {

    public Object getObjectFrom(DependencyDescriptor dependencyDescriptor);
}

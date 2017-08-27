package com.helospark.lightdi.dependencywire.chain;

import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface DependencyWireChain {

    void collectDependencies(List<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependency);

}

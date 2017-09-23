package com.helospark.lightdi.dependencywire.chain;

import java.util.SortedSet;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface DependencyWireChain {

    void collectDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependency);

}

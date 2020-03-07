package com.helospark.lightdi.dependencywire.chain;

import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.dependencywire.chain.support.ConstructorWireSupport;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;

public class ComponentDependencyWireChainItem implements DependencyWireChain {
    private ConstructorWireSupport constructorWireSupport;

    public ComponentDependencyWireChainItem(ConstructorWireSupport constructorWireSupport) {
        this.constructorWireSupport = constructorWireSupport;
    }

    @Override
    public void collectDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptor dependencyDescriptor) {
        try {
            if (canHandle(dependencyDescriptor)) {
                handleInternal(dependencyDescriptors, dependencyDescriptor);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to create " + dependencyDescriptor, e);
        }
    }

    private boolean canHandle(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof StereotypeDependencyDescriptor;
    }

    private void handleInternal(SortedSet<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptor dependencyDescriptor) {
        StereotypeDependencyDescriptor dependency = (StereotypeDependencyDescriptor) dependencyDescriptor;

        Class<?> clazz = dependency.getClazz();
        List<ConstructorDescriptor> constructorDependencies = constructorWireSupport
                .getConstructors(dependencyDescriptors, clazz, dependencyDescriptor);

        dependency.setConstructorDescriptor(constructorDependencies);
    }

}

package com.helospark.lightdi.dependencywire;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.dependencywire.chain.DependencyWireChain;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class WiringProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WiringProcessingService.class);
    private List<DependencyWireChain> wireChain;

    public WiringProcessingService(List<DependencyWireChain> wireChain) {
        this.wireChain = wireChain;
    }

    public void wireTogetherDependencies(List<DependencyDescriptor> dependencyDescriptors) {
        dependencyDescriptors.stream()
                .forEach(dependency -> initializeAllWiring(dependency, dependencyDescriptors));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dependency descriptors are loaded: " + dependencyDescriptors);
        }

    }

    private void initializeAllWiring(DependencyDescriptor dependency,
            List<DependencyDescriptor> dependencyDescriptors) {
        wireChain.stream()
                .forEach(chainItem -> chainItem.collectDependencies(dependencyDescriptors, dependency));
    }

}

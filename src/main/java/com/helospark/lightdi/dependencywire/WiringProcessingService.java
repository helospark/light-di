package com.helospark.lightdi.dependencywire;

import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.dependencywire.chain.DependencyWireChain;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class WiringProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WiringProcessingService.class);
    private List<DependencyWireChain> wireChain;
    private StreamFactory streamFactory;

    public WiringProcessingService(List<DependencyWireChain> wireChain, StreamFactory streamFactory) {
	this.wireChain = wireChain;
	this.streamFactory = streamFactory;
    }

    public void wireTogetherDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors) {
	streamFactory.stream(dependencyDescriptors)
		.forEach(dependency -> initializeAllWiring(dependency, dependencyDescriptors));

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Dependency descriptors are loaded: " + dependencyDescriptors);
	}

    }

    public void initializeAllWiring(DependencyDescriptor dependency,
	    SortedSet<DependencyDescriptor> dependencyDescriptors) {
	wireChain.stream()
		.forEach(chainItem -> chainItem.collectDependencies(dependencyDescriptors, dependency));
    }

}

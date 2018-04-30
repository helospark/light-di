package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.ReflectionUtil.createClassForName;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class BeanDefinitionCollector {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain;
    private StreamFactory streamFactory;

    public BeanDefinitionCollector(List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain,
	    StreamFactory streamFactory) {
	this.beanDefinitionCollectorChain = beanDefinitionCollectorChain;
	this.streamFactory = streamFactory;
    }

    public SortedSet<DependencyDescriptor> collectDependencyDescriptors(List<String> classes) {
	List<Set<DependencyDescriptor>> result = streamFactory.stream(classes)
		.map(className -> createClassForName(className))
		.map(clazz -> collectDefinitions(clazz))
		.collect(Collectors.toList());
	// TODO: Should we put it into the above?
	TreeSet<DependencyDescriptor> setResult = new TreeSet<>();
	result.stream()
		.forEach(a -> setResult.addAll(a));
	return setResult;
    }

    private Set<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
	return beanDefinitionCollectorChain.stream()
		.flatMap(a -> a.collectDefinitions(clazz).stream())
		.collect(Collectors.toSet());
    }
}

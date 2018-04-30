package com.helospark.lightdi.beanfactory;

import static com.helospark.lightdi.util.LogMessageBeanNameFormatter.convertToBeanNameListString;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.lightdi.exception.IllegalConfigurationException;

public class BeanFactory {
    private List<BeanFactoryChainItem> chain;
    private BeanPostConstructInitializer beanPostConstructInitializer;
    private StreamFactory streamFactory;

    public BeanFactory(List<BeanFactoryChainItem> chain, BeanPostConstructInitializer beanPostConstructInitializer,
	    StreamFactory streamFactory) {
	this.chain = chain;
	this.beanPostConstructInitializer = beanPostConstructInitializer;
	this.streamFactory = streamFactory;
    }

    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
	try {
	    Object createdBean = findHandler(dependencyToCreate)
		    .createBean(lightDiContext, dependencyToCreate);
	    beanPostConstructInitializer.postProcessCreatedBean(lightDiContext, dependencyToCreate, createdBean);

	    return createdBean;
	} catch (Exception e) {
	    throw new BeanCreationException("Failed to create bean " + dependencyToCreate, e);
	}
    }

    private BeanFactoryChainItem findHandler(DependencyDescriptor dependencyToCreate) {
	return chain.stream()
		.filter(chainItem -> chainItem.isSupported(dependencyToCreate))
		.findFirst()
		.orElseThrow(() -> new IllegalStateException(
			"Cannot create bean because no bean factory exists for type "
				+ dependencyToCreate.getClass().getName()));
    }

    public void assertValidConfiguration(SortedSet<DependencyDescriptor> earlierInRoute) {
	streamFactory.stream(earlierInRoute)
		.forEach(descriptor -> assertValidConfiguration(descriptor, new ArrayList<>()));
    }

    public void assertValidConfiguration(DependencyDescriptor dependencyToCreate,
	    List<DependencyDescriptor> earlierInRoute) {
	List<DependencyDescriptor> dependencies = findDependenciesFor(dependencyToCreate);

	for (DependencyDescriptor dependency : dependencies) {
	    if (earlierInRoute.contains(dependency)) {
		throw new IllegalConfigurationException(
			"Circle in bean definitions: " + convertToBeanNameListString(earlierInRoute));
	    }
	}

	for (DependencyDescriptor dependency : dependencies) {
	    earlierInRoute.add(dependency);
	    assertValidConfiguration(dependency, earlierInRoute);
	    earlierInRoute.remove(dependency);
	}
    }

    private List<DependencyDescriptor> findDependenciesFor(DependencyDescriptor dependencyToCreate) {
	return findHandler(dependencyToCreate)
		.extractDependencies(dependencyToCreate);
    }

}

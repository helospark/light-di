package com.helospark.lightdi.dependencywire;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class DefinitionIntegrityChecker {
    private LightDiContextConfiguration lightDiContextConfiguration;
    private BeanFactory beanFactory;

    public DefinitionIntegrityChecker(LightDiContextConfiguration lightDiContextConfiguration,
	    BeanFactory beanFactory) {
	this.lightDiContextConfiguration = lightDiContextConfiguration;
	this.beanFactory = beanFactory;
    }

    public void checkIntegrityIfNeeded(SortedSet<DependencyDescriptor> dependencyDescriptors) {
	if (lightDiContextConfiguration.isCheckForIntegrity()) {
	    beanFactory.assertValidConfiguration(dependencyDescriptors);
	}
    }

}

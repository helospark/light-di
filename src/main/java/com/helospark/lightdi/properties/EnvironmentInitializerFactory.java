package com.helospark.lightdi.properties;

import static com.helospark.lightdi.annotation.PropertySource.IGNORE_RESOURCE_NOT_FOUND_ATTRIBUTE_NAME;
import static com.helospark.lightdi.annotation.PropertySource.ORDER_ATTRIBUTE_NAME;
import static com.helospark.lightdi.annotation.PropertySource.VALUE_ATTRIBUTE_NAME;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.NoPropertyFileFoundException;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.LightDiAnnotation;

public class EnvironmentInitializerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentInitializerFactory.class);
    private PropertiesFileLoader propertiesFileLoader;

    public EnvironmentInitializerFactory(PropertiesFileLoader propertiesFileLoader) {
	this.propertiesFileLoader = propertiesFileLoader;
    }

    public Environment initializeEnvironment(Environment environment,
	    SortedSet<DependencyDescriptor> dependencyDescriptors) {
	List<PropertySourceHolder> propertySourceHolders = dependencyDescriptors.stream()
		.filter(descriptor -> doesHavePropertySource(descriptor))
		.flatMap(descriptor -> createPropertySourceResolver(descriptor, environment))
		.collect(Collectors.toList());

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Properties are loaded: " + propertySourceHolders);
	}

	environment.addPropertySources(propertySourceHolders);
	return environment;
    }

    private boolean doesHavePropertySource(DependencyDescriptor descriptor) {
	return AnnotationUtil.hasAnnotation(descriptor.getClazz(), PropertySource.class);
    }

    private Stream<PropertySourceHolder> createPropertySourceResolver(DependencyDescriptor descriptor,
	    Environment environment) {
	return AnnotationUtil
		.getAnnotationsOfType(descriptor.getClazz(), PropertySource.class)
		.stream()
		.flatMap(annotation -> loadPropertySource(annotation, environment));
    }

    private Stream<PropertySourceHolder> loadPropertySource(LightDiAnnotation annotation, Environment environment) {
	return Arrays.stream(annotation.getAttributeAs(VALUE_ATTRIBUTE_NAME, String[].class))
		.map(value -> environment.resolve(value))
		.map(value -> loadProperties(value, annotation)).filter(value -> value.isPresent())
		.map(value -> value.get())
		.map(loadedProperty -> new PropertySourceHolder(
			annotation.getAttributeAs(ORDER_ATTRIBUTE_NAME, Integer.class), loadedProperty));
    }

    private Optional<Map<String, String>> loadProperties(String value, LightDiAnnotation annotation) {
	try {
	    return Optional.of(propertiesFileLoader.load(value));
	} catch (NoPropertyFileFoundException e) {
	    if (annotation.getAttributeAs(IGNORE_RESOURCE_NOT_FOUND_ATTRIBUTE_NAME, Boolean.class)) {
		return Optional.empty();
	    } else {
		throw e;
	    }
	}
    }
}

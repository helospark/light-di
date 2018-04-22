package com.helospark.lightdi.properties;

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
import com.helospark.lightdi.annotation.PropertySources;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.NoPropertyFileFoundException;
import com.helospark.lightdi.util.AnnotationUtil;

public class EnvironmentInitializerFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentInitializerFactory.class);
	private PropertiesFileLoader propertiesFileLoader;

	public EnvironmentInitializerFactory() {
		this.propertiesFileLoader = new PropertiesFileLoader();
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
		return AnnotationUtil.hasAnnotation(descriptor.getClazz(), PropertySource.class)
				|| AnnotationUtil.hasAnnotation(descriptor.getClazz(), PropertySources.class);
	}

	private Stream<PropertySourceHolder> createPropertySourceResolver(DependencyDescriptor descriptor,
			Environment environment) {
		Stream<PropertySource> propertySourceAnnotationStream = AnnotationUtil
				.getAnnotationsOfType(descriptor.getClazz(), PropertySource.class).stream();
		Stream<PropertySource> propertySourcesStream = AnnotationUtil
				.getAnnotationsOfType(descriptor.getClazz(), PropertySources.class).stream()
				.flatMap(annotation -> Arrays.stream(annotation.value()));

		return Stream.concat(propertySourceAnnotationStream, propertySourcesStream)
				.flatMap(annotation -> loadPropertySource(annotation, environment));
	}

	private Stream<PropertySourceHolder> loadPropertySource(PropertySource annotation, Environment environment) {
		return Arrays.stream(annotation.value()).map(value -> environment.resolve(value))
				.map(value -> loadProperties(value, annotation)).filter(value -> value.isPresent())
				.map(value -> value.get())
				.map(loadedProperty -> new PropertySourceHolder(annotation.order(), loadedProperty));
	}

	private Optional<Map<String, String>> loadProperties(String value, PropertySource annotation) {
		try {
			return Optional.of(propertiesFileLoader.load(value));
		} catch (NoPropertyFileFoundException e) {
			if (annotation.ignoreResourceNotFound()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}
}

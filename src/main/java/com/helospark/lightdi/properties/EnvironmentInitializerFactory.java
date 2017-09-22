package com.helospark.lightdi.properties;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class EnvironmentInitializerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentInitializerFactory.class);
    private PropertiesFileLoader propertiesFileLoader;

    public EnvironmentInitializerFactory() {
        this.propertiesFileLoader = new PropertiesFileLoader();
    }

    public Environment initializeEnvironment(Environment environment, SortedSet<DependencyDescriptor> dependencyDescriptors) {
        List<PropertySourceHolder> propertySourceHolders = dependencyDescriptors.stream()
                .filter(descriptor -> doesHavePropertySource(descriptor))
                .flatMap(descriptor -> createPropertySourceResolver(descriptor))
                .collect(Collectors.toList());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Properties are loaded: " + propertySourceHolders);
        }

        environment.addPropertySources(propertySourceHolders);
        return environment;
    }

    private boolean doesHavePropertySource(DependencyDescriptor descriptor) {
        return descriptor.getClazz().getAnnotationsByType(PropertySource.class).length > 0;
    }

    private Stream<PropertySourceHolder> createPropertySourceResolver(DependencyDescriptor descriptor) {
        PropertySource[] annotations = descriptor.getClazz().getAnnotationsByType(PropertySource.class);
        return Arrays.stream(annotations)
                .flatMap(annotation -> loadPropertySource(annotation));
    }

    private Stream<PropertySourceHolder> loadPropertySource(PropertySource annotation) {
        return Arrays.stream(annotation.value())
                .map(value -> propertiesFileLoader.load(value))
                .map(loadedProperty -> new PropertySourceHolder(annotation.order(), loadedProperty));
    }
}

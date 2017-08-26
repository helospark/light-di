package com.helospark.lightdi.properties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ValueResolverFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValueResolverFactory.class);
    private PropertiesFileLoader propertiesFileLoader;

    public ValueResolverFactory() {
        this.propertiesFileLoader = new PropertiesFileLoader();
    }

    public ValueResolver createValueResolver(List<DependencyDescriptor> dependencyDescriptors) {
        List<PropertySourceHolder> propertySourceHolders = dependencyDescriptors.stream()
                .filter(descriptor -> doesHavePropertySource(descriptor))
                .flatMap(descriptor -> createPropertySourceResolver(descriptor))
                .collect(Collectors.toList());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Properties are loaded: " + propertySourceHolders);
        }

        PropertyValueResolver propertyValueResolver = new PropertyValueResolver(propertySourceHolders);
        PropertyStringResolver propertyStringResolver = new PropertyStringResolver(propertyValueResolver);
        return new ValueResolver(propertyStringResolver);
    }

    private boolean doesHavePropertySource(DependencyDescriptor descriptor) {
        return descriptor.getClazz().getAnnotationsByType(PropertySource.class).length > 0;
    }

    private Stream<PropertySourceHolder> createPropertySourceResolver(DependencyDescriptor descriptor) {
        PropertySource[] annotation = descriptor.getClazz().getAnnotationsByType(PropertySource.class);
        return Arrays.stream(annotation)
                .map(propertySourceAnnotation -> propertySourceAnnotation.value())
                .flatMap(value -> Arrays.stream(value))
                .map(value -> propertiesFileLoader.load(value))
                .map(value -> new PropertySourceHolder(value));
    }
}

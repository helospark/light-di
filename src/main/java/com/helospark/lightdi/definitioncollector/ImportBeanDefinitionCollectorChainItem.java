package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Import;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ImportBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {
    private ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem;

    public ImportBeanDefinitionCollectorChainItem(
            ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem) {
        this.configurationClassBeanDefinitionCollectorChainItem = configurationClassBeanDefinitionCollectorChainItem;
    }

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        if (isSupported(clazz)) {
            return AnnotationUtil.getAnnotationsOfType(clazz, Import.class)
                    .stream()
                    .flatMap(importAnnotation -> processImport(importAnnotation, clazz))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Stream<DependencyDescriptor> processImport(LightDiAnnotation annotation, Class<?> importingClass) {
        Class<?>[] importedConfiguration = annotation.getAttributeAs(Import.ATTRIBUTE_NAME, Class[].class);
        return Arrays.stream(importedConfiguration)
                .flatMap(a -> processSingleClass(a, importingClass));
    }

    private Stream<DependencyDescriptor> processSingleClass(Class<?> importedConfiguration, Class<?> importingClass) {
        if (!hasAnnotation(importedConfiguration, Configuration.class)) {
            throw new IllegalConfigurationException("Importing a class that is not a configuration");
        }
        return configurationClassBeanDefinitionCollectorChainItem.collectDefinitions(importedConfiguration)
                .stream()
                .map(element -> {
                    element.setImportingClass(Optional.of(importingClass));
                    return element;
                });
    }

    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Import.class);
    }
}

package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.util.Collections;
import java.util.List;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Import;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.AnnotationUtil;

public class ImportBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {
    private ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem;

    public ImportBeanDefinitionCollectorChainItem(
            ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem) {
        this.configurationClassBeanDefinitionCollectorChainItem = configurationClassBeanDefinitionCollectorChainItem;
    }

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        if (isSupported(clazz)) {
            Import importAnnotation = AnnotationUtil.getSingleAnnotationOfType(clazz, Import.class);
            Class<?> importedConfiguration = importAnnotation.value();
            if (!hasAnnotation(importedConfiguration, Configuration.class)) {
                throw new IllegalConfigurationException("Importing a class that is not a configuration");
            }
            return configurationClassBeanDefinitionCollectorChainItem.collectDefinitions(importedConfiguration);
        } else {
            return Collections.emptyList();
        }
    }

    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Import.class);
    }
}

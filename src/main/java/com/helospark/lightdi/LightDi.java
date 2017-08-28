package com.helospark.lightdi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.beanfactory.BeanFactoryFactory;
import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.definitioncollector.BeanDefinitionProcessorChainFactory;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.dependencywire.WiringProcessingServiceFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.properties.ValueResolverFactory;
import com.helospark.lightdi.scanner.LightDiClasspathScanner;

public class LightDi {
    private static final Logger LOGGER = LoggerFactory.getLogger(LightDi.class);
    private BeanFactory beanFactory;
    private WiringProcessingService wiringProcessingService;
    private BeanDefinitionCollector beanDefinitionCollector;
    private ValueResolverFactory valueResolverFactory;
    private LightDiClasspathScanner lightDiClasspathScanner;

    public LightDi() {
        lightDiClasspathScanner = new LightDiClasspathScanner();

        BeanDefinitionProcessorChainFactory beanDefinitionProcessorChainFactory = new BeanDefinitionProcessorChainFactory();
        beanDefinitionCollector = beanDefinitionProcessorChainFactory.createBeanDefinitionProcessorChain();

        BeanFactoryFactory beanFactoryFactory = new BeanFactoryFactory();
        beanFactory = beanFactoryFactory.createBeanFactory();

        WiringProcessingServiceFactory preprocessWireServiceFactory = new WiringProcessingServiceFactory();
        wiringProcessingService = preprocessWireServiceFactory.createFieldWireSupport();

        valueResolverFactory = new ValueResolverFactory();
    }

    public LightDiContext initContextByPackage(String packageName) {
        return initInternal(packageName);
    }

    private LightDiContext initInternal(String packageName) {
        try {
            List<String> classes = lightDiClasspathScanner.scanClasspathForBeanClassNames(packageName);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Following classes are annotated for LightDI: " + classes);
            }

            List<DependencyDescriptor> dependencyDescriptors = beanDefinitionCollector
                    .collectDependencyDescriptors(classes);
            wiringProcessingService.wireTogetherDependencies(dependencyDescriptors);
            ValueResolver valueResolver = valueResolverFactory.createValueResolver(dependencyDescriptors);

            LOGGER.info("Context initialized");

            return new LightDiContext(dependencyDescriptors, valueResolver, beanFactory);
        } catch (Exception e) {
            throw new ContextInitializationFailedException("Context initialization failed", e);
        }
    }

}
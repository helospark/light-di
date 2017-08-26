package com.helospark.lightdi;

import java.util.List;

import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.definitioncollector.BeanDefinitionProcessorChainFactory;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.dependencywire.WiringProcessingServiceFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.properties.ValueResolverFactory;
import com.helospark.lightdi.scanner.LightDiClasspathScanner;

public class LightDi {
    private BeanFactory beanFactory;
    private WiringProcessingService preprocessWiringService;
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
        preprocessWiringService = preprocessWireServiceFactory.createFieldWireSupport();

        valueResolverFactory = new ValueResolverFactory();
    }

    public LightDiContext initContextByPackage(String packageName) {
        return initInternal(packageName);
    }

    private LightDiContext initInternal(String packageName) {
        List<String> classes = lightDiClasspathScanner.scanClasspathForBeanClassNames(packageName);

        try {
            // phase 1
            List<DependencyDescriptor> dependencyDescriptors = beanDefinitionCollector
                    .collectDependencyDescriptors(classes);

            // phase 2
            preprocessWiringService.wireTogetherDependencies(dependencyDescriptors);

            // Properties phase
            ValueResolver valueResolver = valueResolverFactory.createValueResolver(dependencyDescriptors);

            // phase 3
            return new LightDiContext(dependencyDescriptors, valueResolver, beanFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
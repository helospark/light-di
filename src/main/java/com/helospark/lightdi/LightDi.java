package com.helospark.lightdi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.beanfactory.BeanFactoryFactory;
import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.definitioncollector.BeanDefinitionProcessorChainFactory;
import com.helospark.lightdi.dependencywire.ComponentScanCollector;
import com.helospark.lightdi.dependencywire.RecursiveDependencyDescriptorCollector;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.dependencywire.WiringProcessingServiceFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.properties.ValueResolverFactory;
import com.helospark.lightdi.scanner.LightDiClasspathScanner;
import com.helospark.lightdi.util.AutowirePostProcessor;

public class LightDi {
    private static final Logger LOGGER = LoggerFactory.getLogger(LightDi.class);
    private BeanFactory beanFactory;
    private WiringProcessingService wiringProcessingService;
    private BeanDefinitionCollector beanDefinitionCollector;
    private ValueResolverFactory valueResolverFactory;
    private LightDiClasspathScanner lightDiClasspathScanner;
    private RecursiveDependencyDescriptorCollector recursiveDependencyDescriptorCollector;

    private WiringProcessingServiceFactory preprocessWireServiceFactory;
    private BeanFactoryFactory beanFactoryFactory;
    private BeanDefinitionProcessorChainFactory beanDefinitionProcessorChainFactory;

    public LightDi() {
        lightDiClasspathScanner = new LightDiClasspathScanner();

        beanDefinitionProcessorChainFactory = new BeanDefinitionProcessorChainFactory();
        beanDefinitionCollector = beanDefinitionProcessorChainFactory.createBeanDefinitionProcessorChain();

        beanFactoryFactory = new BeanFactoryFactory();
        beanFactory = beanFactoryFactory.createBeanFactory();

        preprocessWireServiceFactory = new WiringProcessingServiceFactory();
        wiringProcessingService = preprocessWireServiceFactory.createFieldWireSupport();

        valueResolverFactory = new ValueResolverFactory();

        ComponentScanCollector componentScanCollector = new ComponentScanCollector();

        recursiveDependencyDescriptorCollector = new RecursiveDependencyDescriptorCollector(lightDiClasspathScanner, beanDefinitionCollector,
                componentScanCollector);
    }

    public LightDiContext initContextByPackage(String packageName) {
        return initInternal(packageName);
    }

    private LightDiContext initInternal(String packageName) {
        try {
            List<DependencyDescriptor> dependencyDescriptors = recursiveDependencyDescriptorCollector.collectDependencies(packageName);

            wiringProcessingService.wireTogetherDependencies(dependencyDescriptors);
            ValueResolver valueResolver = valueResolverFactory.createValueResolver(dependencyDescriptors);

            LightDiContext context = new LightDiContext(dependencyDescriptors, valueResolver, beanFactory);

            // TODO: think of better way to do this
            AutowirePostProcessor autowireSupportUtil = new AutowirePostProcessor(
                    beanDefinitionProcessorChainFactory.getStereotypeBeanDefinitionCollectorChainItem(),
                    preprocessWireServiceFactory.createFieldWireSupport(), beanFactoryFactory.getAutowirePostProcessSupport(),
                    context);
            context.setAutowireSupportUtil(autowireSupportUtil);

            initializeEagerDependencies(context, dependencyDescriptors);

            LOGGER.info("Context initialized");

            return context;
        } catch (Exception e) {
            throw new ContextInitializationFailedException("Context initialization failed", e);
        }
    }

    private void initializeEagerDependencies(LightDiContext context, List<DependencyDescriptor> dependencyDescriptors) {
        dependencyDescriptors.stream()
                .filter(dependency -> !dependency.isLazy())
                .forEach(dependency -> context.getBean(dependency));
    }

}
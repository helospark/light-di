package com.helospark.lightdi.util;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.definitioncollector.StereotypeBeanDefinitionCollectorChainItem;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;

public class AutowirePostProcessor {
    private StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem;
    private WiringProcessingService wiringProcessingService;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private LightDiContext context;
    private BeanPostConstructInitializer beanPostConstructInitializer;

    public AutowirePostProcessor(StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem,
            WiringProcessingService wiringProcessingService, AutowirePostProcessSupport autowirePostProcessSupport,
            BeanPostConstructInitializer beanPostConstructInitializer,
            LightDiContext context) {
        this.stereotypeBeanDefinitionCollectorChainItem = stereotypeBeanDefinitionCollectorChainItem;
        this.wiringProcessingService = wiringProcessingService;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.beanPostConstructInitializer = beanPostConstructInitializer;
        this.context = context;
    }

    public void autowireFieldsTo(Object instance) {
        List<DependencyDescriptor> definition = stereotypeBeanDefinitionCollectorChainItem
                .collectDefinitionsOnNonStereotypeClass(instance.getClass());
        StereotypeDependencyDescriptor actualDependencyDescriptor = (StereotypeDependencyDescriptor) definition.get(0);
        wiringProcessingService.initializeAllWiring(actualDependencyDescriptor, context.getDependencyDescriptors());
        autowirePostProcessSupport.injectAutowired(context, actualDependencyDescriptor, instance);
        beanPostConstructInitializer.postProcessCreatedBean(context, actualDependencyDescriptor, instance);
    }

}

package com.helospark.lightdi.util;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.definitioncollector.StereotypeBeanDefinitionCollectorChainItem;
import com.helospark.lightdi.dependencywire.WiringProcessingService;

public class AutowirePostProcessorFactory {
    private StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem;
    private WiringProcessingService wiringProcessingService;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private BeanPostConstructInitializer beanPostConstructInitializer;

    public AutowirePostProcessorFactory(StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem, WiringProcessingService wiringProcessingService,
            AutowirePostProcessSupport autowirePostProcessSupport, BeanPostConstructInitializer beanPostConstructInitializer) {
        this.stereotypeBeanDefinitionCollectorChainItem = stereotypeBeanDefinitionCollectorChainItem;
        this.wiringProcessingService = wiringProcessingService;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.beanPostConstructInitializer = beanPostConstructInitializer;
    }

    public AutowirePostProcessor create(LightDiContext context) {
        return new AutowirePostProcessor(stereotypeBeanDefinitionCollectorChainItem,
                wiringProcessingService,
                autowirePostProcessSupport,
                beanPostConstructInitializer,
                context);
    }
}

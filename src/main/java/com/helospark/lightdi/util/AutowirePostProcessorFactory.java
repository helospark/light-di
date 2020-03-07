package com.helospark.lightdi.util;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.dependencywire.WiringProcessingService;

public class AutowirePostProcessorFactory {
    private WiringProcessingService wiringProcessingService;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private BeanPostConstructInitializer beanPostConstructInitializer;

    public AutowirePostProcessorFactory(WiringProcessingService wiringProcessingService,
            AutowirePostProcessSupport autowirePostProcessSupport, BeanPostConstructInitializer beanPostConstructInitializer) {
        this.wiringProcessingService = wiringProcessingService;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.beanPostConstructInitializer = beanPostConstructInitializer;
    }

    public AutowirePostProcessor create(LightDiContext context) {
        return new AutowirePostProcessor(wiringProcessingService,
                autowirePostProcessSupport,
                beanPostConstructInitializer,
                context);
    }
}

package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;

public class AutowirePostProcessor {
    private WiringProcessingService wiringProcessingService;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private LightDiContext context;
    private BeanPostConstructInitializer beanPostConstructInitializer;

    public AutowirePostProcessor(WiringProcessingService wiringProcessingService, AutowirePostProcessSupport autowirePostProcessSupport,
            BeanPostConstructInitializer beanPostConstructInitializer,
            LightDiContext context) {
        this.wiringProcessingService = wiringProcessingService;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.beanPostConstructInitializer = beanPostConstructInitializer;
        this.context = context;
    }

    public void autowireFieldsTo(Object instance) {
        Class<?> clazz = instance.getClass();
        DependencyDescriptor actualDependencyDescriptor = ManualDependencyDescriptor.builder()
                .withClazz(clazz)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(clazz))
                .withScope(QualifierExtractor.extractScope(clazz))
                .withIsLazy(IsLazyExtractor.isLazy(clazz))
                .withIsPrimary(IsPrimaryExtractor.isPrimary(clazz))
                .withMergedAnnotations(AnnotationUtil.getAllMergedAnnotations(clazz))
                .withOrder(BeanOrderExtractor.extractOrder(clazz))
                .build();
        wiringProcessingService.initializeAllWiring(actualDependencyDescriptor, context.getDependencyDescriptors());
        autowirePostProcessSupport.injectAutowired(context, actualDependencyDescriptor, instance);
        beanPostConstructInitializer.postProcessCreatedBean(context, actualDependencyDescriptor, instance);
    }

}

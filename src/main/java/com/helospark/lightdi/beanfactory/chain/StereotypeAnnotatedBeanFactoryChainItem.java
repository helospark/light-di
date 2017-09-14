package com.helospark.lightdi.beanfactory.chain;

import java.util.ArrayList;
import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.beanfactory.chain.support.InjectDescriptorsToDependencies;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;

public class StereotypeAnnotatedBeanFactoryChainItem implements BeanFactoryChainItem {
    private ConstructorInvoker constructorInvoker;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private InjectDescriptorsToDependencies injectDescriptorsToDependencies;

    public StereotypeAnnotatedBeanFactoryChainItem(ConstructorInvoker constructorInvoker, AutowirePostProcessSupport autowirePostProcessSupport,
            InjectDescriptorsToDependencies injectDescriptorsToDependencies) {
        this.constructorInvoker = constructorInvoker;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.injectDescriptorsToDependencies = injectDescriptorsToDependencies;
    }

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) throws Exception {
        StereotypeDependencyDescriptor stereotypeDependency = (StereotypeDependencyDescriptor) dependencyToCreate;
        Object result = constructorInvoker.invokeConstructor(lightDiContext, stereotypeDependency);
        autowirePostProcessSupport.injectAutowired(lightDiContext, stereotypeDependency, result);
        return result;
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof StereotypeDependencyDescriptor;
    }

    @Override
    public List<DependencyDescriptor> extractDependencies(DependencyDescriptor dependencyToCreate) {
        StereotypeDependencyDescriptor stereotypeDependency = (StereotypeDependencyDescriptor) dependencyToCreate;
        List<DependencyDescriptor> result = new ArrayList<>();
        result.addAll(injectDescriptorsToDependencies
                .extract(stereotypeDependency.getConstructorDescriptor().stream().map(a -> a.getDependencyDescriptor())));
        result.addAll(
                injectDescriptorsToDependencies.extract(stereotypeDependency.getFieldDescriptor().stream().map(a -> a.getInjectionDescriptor())));
        result.addAll(injectDescriptorsToDependencies
                .extract(stereotypeDependency.getSetterDescriptor().stream().flatMap(a -> a.getInjectionDescriptor().stream())));
        return result;
    }
}

package com.helospark.lightdi.beanfactory.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;

public class StereotypeAnnotatedBeanFactoryChainItem implements BeanFactoryChainItem {
    private ConstructorInvoker constructorInvoker;
    private AutowirePostProcessSupport autowirePostProcessSupport;

    public StereotypeAnnotatedBeanFactoryChainItem(ConstructorInvoker constructorInvoker, AutowirePostProcessSupport autowirePostProcessSupport) {
        this.constructorInvoker = constructorInvoker;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
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
        result.addAll(streamToInject(stereotypeDependency.getConstructorDescriptor().stream().map(a -> a.getDependencyDescriptor())));
        result.addAll(streamToInject(stereotypeDependency.getFieldDescriptor().stream().map(a -> a.getInjectionDescriptor())));
        result.addAll(streamToInject(stereotypeDependency.getSetterDescriptor().stream().flatMap(a -> a.getInjectionDescriptor().stream())));
        return result;
    }

    private List<DependencyDescriptor> streamToInject(Stream<InjectionDescriptor> list) {
        return list.filter(injectDescriptor -> injectDescriptor instanceof DependencyDescriptor)
                .map(injectDescriptor -> (DependencyDescriptor) injectDescriptor)
                .collect(Collectors.toList());
    }
}

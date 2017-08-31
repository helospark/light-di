package com.helospark.lightdi.beanfactory.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;

public class StereotypeAnnotatedBeanFactoryChainItem implements BeanFactoryChainItem {
    private ConstructorInvoker constructorInvoker;
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private PostConstructInvoker postConstructInvoker;

    public StereotypeAnnotatedBeanFactoryChainItem(ConstructorInvoker constructorInvoker, AutowirePostProcessSupport autowirePostProcessSupport,
            PostConstructInvoker postConstructInvoker) {
        this.constructorInvoker = constructorInvoker;
        this.autowirePostProcessSupport = autowirePostProcessSupport;
        this.postConstructInvoker = postConstructInvoker;
    }

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) throws Exception {
        StereotypeDependencyDescriptor stereotypeDependency = (StereotypeDependencyDescriptor) dependencyToCreate;
        Object result = constructorInvoker.invokeConstructor(lightDiContext, stereotypeDependency);
        autowirePostProcessSupport.injectAutowired(lightDiContext, stereotypeDependency, result);
        postConstructInvoker.invokePostConstructMethods(stereotypeDependency, result);
        return result;
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof StereotypeDependencyDescriptor;
    }
}

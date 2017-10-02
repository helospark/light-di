package com.helospark.lightdi.beanfactory.chain;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.support.InjectDescriptorsToDependencies;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.reflection.MethodInvoker;

public class ConfigurationBeanFacotoryChainItem implements BeanFactoryChainItem {
    private MethodInvoker methodInvoker;
    private InjectDescriptorsToDependencies injectDescriptorsToDependencies;

    public ConfigurationBeanFacotoryChainItem(MethodInvoker methodInvoker,
            InjectDescriptorsToDependencies injectDescriptorsToDependencies) {
        this.methodInvoker = methodInvoker;
        this.injectDescriptorsToDependencies = injectDescriptorsToDependencies;
    }

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        BeanDependencyDescriptor beanDependencyDescriptor = (BeanDependencyDescriptor) dependencyToCreate;
        Object configurationBean = lightDiContext
                .getBean(beanDependencyDescriptor.getConfigurationDescriptor());
        return methodInvoker.invokeMethod(lightDiContext, beanDependencyDescriptor.getMethodDescriptor(),
                configurationBean, dependencyToCreate);
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof BeanDependencyDescriptor;
    }

    @Override
    public List<DependencyDescriptor> extractDependencies(DependencyDescriptor dependencyToCreate) {
        BeanDependencyDescriptor beanDependencyDescriptor = (BeanDependencyDescriptor) dependencyToCreate;
        return injectDescriptorsToDependencies.extract(beanDependencyDescriptor.getMethodDescriptor().getInjectionDescriptor());
    }
}

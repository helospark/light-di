package com.helospark.lightdi.beanfactory.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.reflection.MethodInvoker;

public class ConfigurationBeanFacotoryChainItem implements BeanFactoryChainItem {
    private MethodInvoker methodInvoker;

    public ConfigurationBeanFacotoryChainItem(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        BeanDependencyDescriptor beanDependencyDescriptor = (BeanDependencyDescriptor) dependencyToCreate;
        Object configurationBean = lightDiContext
                .getBean(beanDependencyDescriptor.getConfigurationDescriptor());
        return methodInvoker.invokeMethod(lightDiContext, beanDependencyDescriptor.getMethodDescriptor(),
                configurationBean);
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof BeanDependencyDescriptor;
    }
}

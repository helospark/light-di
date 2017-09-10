package com.helospark.lightdi.beanfactory.chain;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface BeanFactoryChainItem {
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) throws Exception;

    public boolean isSupported(DependencyDescriptor dependencyDescriptor);

    public List<DependencyDescriptor> extractDependencies(DependencyDescriptor dependencyToCreate);
}

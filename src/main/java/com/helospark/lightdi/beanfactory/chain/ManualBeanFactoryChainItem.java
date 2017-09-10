package com.helospark.lightdi.beanfactory.chain;

import java.util.Collections;
import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;

// TODO: Seems like it is a hack, think of a better way
public class ManualBeanFactoryChainItem implements BeanFactoryChainItem {

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) throws Exception {
        throw new IllegalStateException("Not supported");
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof ManualDependencyDescriptor;
    }

    @Override
    public List<DependencyDescriptor> extractDependencies(DependencyDescriptor dependencyToCreate) {
        return Collections.emptyList();
    }

}

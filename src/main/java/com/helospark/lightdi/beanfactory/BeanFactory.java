package com.helospark.lightdi.beanfactory;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.beanfactory.chain.BeanFactoryChainItem;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.BeanCreationException;

public class BeanFactory {
    private List<BeanFactoryChainItem> chain;

    public BeanFactory(List<BeanFactoryChainItem> chain) {
        this.chain = chain;
    }

    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        try {
            return chain.stream()
                    .filter(chainItem -> chainItem.isSupported(dependencyToCreate))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Cannot create bean because no bean factory exists for type "
                                    + dependencyToCreate.getClass().getName()))
                    .createBean(lightDiContext, dependencyToCreate);
        } catch (Exception e) {
            throw new BeanCreationException("Failed to create bean " + dependencyToCreate, e);
        }
    }

}

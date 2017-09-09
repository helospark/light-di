package com.helospark.lightdi.beanfactory;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.aware.ContextAware;
import com.helospark.lightdi.beanfactory.chain.BeanFactoryChainItem;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.lightdi.reflection.PostConstructInvoker;

public class BeanFactory {
    private List<BeanFactoryChainItem> chain;
    private PostConstructInvoker postConstructInvoker;

    public BeanFactory(List<BeanFactoryChainItem> chain, PostConstructInvoker postConstructInvoker) {
        this.chain = chain;
        this.postConstructInvoker = postConstructInvoker;
    }

    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        try {
            Object createdBean = chain.stream()
                    .filter(chainItem -> chainItem.isSupported(dependencyToCreate))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Cannot create bean because no bean factory exists for type "
                                    + dependencyToCreate.getClass().getName()))
                    .createBean(lightDiContext, dependencyToCreate);

            postProcessCreatedBean(lightDiContext, dependencyToCreate, createdBean);

            return createdBean;
        } catch (Exception e) {
            throw new BeanCreationException("Failed to create bean " + dependencyToCreate, e);
        }
    }

    private void postProcessCreatedBean(LightDiContext context, DependencyDescriptor dependencyToCreate, Object createdBean) {
        if (createdBean instanceof ContextAware) {
            ((ContextAware) createdBean).setContext(context);
        }
        postConstructInvoker.invokePostConstructMethods(dependencyToCreate, createdBean);
    }

}

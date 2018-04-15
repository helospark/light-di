package com.helospark.lightdi.beanfactory;

import static com.helospark.lightdi.util.LogMessageBeanNameFormatter.convertToBeanNameListString;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.aware.ContextAware;
import com.helospark.lightdi.beanfactory.chain.BeanFactoryChainItem;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.lightdi.exception.IllegalConfigurationException;
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
            Object createdBean = findHandler(dependencyToCreate)
                    .createBean(lightDiContext, dependencyToCreate);

            postProcessCreatedBean(lightDiContext, dependencyToCreate, createdBean);

            return createdBean;
        } catch (Exception e) {
            throw new BeanCreationException("Failed to create bean " + dependencyToCreate, e);
        }
    }

    private BeanFactoryChainItem findHandler(DependencyDescriptor dependencyToCreate) {
        return chain.stream()
                .filter(chainItem -> chainItem.isSupported(dependencyToCreate))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot create bean because no bean factory exists for type "
                                + dependencyToCreate.getClass().getName()));
    }

    public void assertValidConfiguration(SortedSet<DependencyDescriptor> earlierInRoute) {
        for (DependencyDescriptor descriptor : earlierInRoute) {
            assertValidConfiguration(descriptor, new ArrayList<>());
        }
    }

    public void assertValidConfiguration(DependencyDescriptor dependencyToCreate, List<DependencyDescriptor> earlierInRoute) {
        List<DependencyDescriptor> dependencies = findDependenciesFor(dependencyToCreate);

        for (DependencyDescriptor dependency : dependencies) {
            if (earlierInRoute.contains(dependency)) {
                throw new IllegalConfigurationException("Circle in bean definitions: " + convertToBeanNameListString(earlierInRoute));
            }
        }

        for (DependencyDescriptor dependency : dependencies) {
            earlierInRoute.add(dependency);
            assertValidConfiguration(dependency, earlierInRoute);
            earlierInRoute.remove(dependency);
        }
    }

    private List<DependencyDescriptor> findDependenciesFor(DependencyDescriptor dependencyToCreate) {
        return findHandler(dependencyToCreate)
                .extractDependencies(dependencyToCreate);
    }

    private void postProcessCreatedBean(LightDiContext context, DependencyDescriptor dependencyToCreate, Object createdBean) {
        if (createdBean instanceof ContextAware) {
            ((ContextAware) createdBean).setContext(context);
        }
        postConstructInvoker.invokePostConstructMethods(dependencyToCreate, createdBean);
    }

}

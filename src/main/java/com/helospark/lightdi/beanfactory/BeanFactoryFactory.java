package com.helospark.lightdi.beanfactory;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.helospark.lightdi.beanfactory.chain.ConfigurationBeanFacotoryChainItem;
import com.helospark.lightdi.beanfactory.chain.ManualBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.StereotypeAnnotatedBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.MethodInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.reflection.chain.DependentObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;

public class BeanFactoryFactory {
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private ConfigurationBeanFacotoryChainItem configurationBeanFacotoryChainItem;
    private StereotypeAnnotatedBeanFactoryChainItem stereotypeAnnotatedBeanFactoryChainItem;
    private ManualBeanFactoryChainItem manualBeanFactoryChainItem;
    private PostConstructInvoker postConstructInvoker;

    public BeanFactoryFactory() {
        DependentObjectResolverChainItem dependentObjectResolverChainItem = new DependentObjectResolverChainItem();
        PropertyObjectResolverChainItem propertyObjectResolverChainItem = new PropertyObjectResolverChainItem();

        DependencyObjectResolverHandler dependencyObjectResolverHandler = new DependencyObjectResolverHandler(
                Arrays.asList(dependentObjectResolverChainItem, propertyObjectResolverChainItem));

        ConstructorInvoker constructorInvoker = new ConstructorInvoker(dependencyObjectResolverHandler);
        MethodInvoker methodInvoker = new MethodInvoker(dependencyObjectResolverHandler);
        SetterInvoker setterInvoker = new SetterInvoker(methodInvoker);
        FieldSetInvoker fieldSetInvoker = new FieldSetInvoker(dependencyObjectResolverHandler);

        postConstructInvoker = new PostConstructInvoker();
        autowirePostProcessSupport = new AutowirePostProcessSupport(setterInvoker, fieldSetInvoker);
        stereotypeAnnotatedBeanFactoryChainItem = new StereotypeAnnotatedBeanFactoryChainItem(
                constructorInvoker, autowirePostProcessSupport);
        manualBeanFactoryChainItem = new ManualBeanFactoryChainItem();
        configurationBeanFacotoryChainItem = new ConfigurationBeanFacotoryChainItem(
                methodInvoker);
    }

    public BeanFactory createBeanFactory() {
        return new BeanFactory(asList(stereotypeAnnotatedBeanFactoryChainItem, configurationBeanFacotoryChainItem, manualBeanFactoryChainItem),
                postConstructInvoker);
    }

    public AutowirePostProcessSupport getAutowirePostProcessSupport() {
        return autowirePostProcessSupport;
    }

}

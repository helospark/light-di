package com.helospark.lightdi.beanfactory;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.helospark.lightdi.beanfactory.chain.ConfigurationBeanFacotoryChainItem;
import com.helospark.lightdi.beanfactory.chain.StereotypeAnnotatedBeanFactoryChainItem;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.MethodInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.reflection.chain.DependentObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;

public class BeanFactoryFactory {

    public BeanFactory createBeanFactory() {
        DependentObjectResolverChainItem dependentObjectResolverChainItem = new DependentObjectResolverChainItem();
        PropertyObjectResolverChainItem propertyObjectResolverChainItem = new PropertyObjectResolverChainItem();

        DependencyObjectResolverHandler dependencyObjectResolverHandler = new DependencyObjectResolverHandler(
                Arrays.asList(dependentObjectResolverChainItem, propertyObjectResolverChainItem));

        ConstructorInvoker constructorInvoker = new ConstructorInvoker(dependencyObjectResolverHandler);
        MethodInvoker methodInvoker = new MethodInvoker(dependencyObjectResolverHandler);
        SetterInvoker setterInvoker = new SetterInvoker(methodInvoker);
        FieldSetInvoker fieldSetInvoker = new FieldSetInvoker(dependencyObjectResolverHandler);

        PostConstructInvoker postConstructInvoker = new PostConstructInvoker();

        StereotypeAnnotatedBeanFactoryChainItem stereotypeAnnotatedBeanFactoryChainItem = new StereotypeAnnotatedBeanFactoryChainItem(
                constructorInvoker, setterInvoker, fieldSetInvoker, postConstructInvoker);
        ConfigurationBeanFacotoryChainItem configurationBeanFacotoryChainItem = new ConfigurationBeanFacotoryChainItem(
                methodInvoker);

        return new BeanFactory(asList(stereotypeAnnotatedBeanFactoryChainItem, configurationBeanFacotoryChainItem));
    }
}

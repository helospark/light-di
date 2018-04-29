package com.helospark.lightdi.beanfactory;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.ConfigurationBeanFacotoryChainItem;
import com.helospark.lightdi.beanfactory.chain.ManualBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.StereotypeAnnotatedBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.beanfactory.chain.support.InjectDescriptorsToDependencies;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.MethodInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;
import com.helospark.lightdi.reflection.aware.InterfaceAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.BeanNameAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.ContextAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.EnvironmentAwareInjector;
import com.helospark.lightdi.reflection.chain.DependencyCollectionResolverChainItem;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.reflection.chain.DependentObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.NullInjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.SpecialValueObjectResolverChainItem;
import com.helospark.lightdi.util.CollectionFactory;

public class BeanFactoryFactory {
    private AutowirePostProcessSupport autowirePostProcessSupport;
    private ConfigurationBeanFacotoryChainItem configurationBeanFacotoryChainItem;
    private StereotypeAnnotatedBeanFactoryChainItem stereotypeAnnotatedBeanFactoryChainItem;
    private ManualBeanFactoryChainItem manualBeanFactoryChainItem;

    private EnvironmentAwareInjector environmentAwareInjector;
    private BeanNameAwareInjector beanNameAwareInjector;
    private ContextAwareInjector contextAwareInjector;
    private InterfaceAwareInjector interfaceAwareInjector;

    private PostConstructInvoker postConstructInvoker;
    private BeanPostConstructInitializer beanPostConstructInitializer;

    public BeanFactoryFactory() {
        DependentObjectResolverChainItem dependentObjectResolverChainItem = new DependentObjectResolverChainItem();
        PropertyObjectResolverChainItem propertyObjectResolverChainItem = new PropertyObjectResolverChainItem();
        DependencyCollectionResolverChainItem dependencyCollectionResolverChainItem = new DependencyCollectionResolverChainItem(
                new CollectionFactory());
        NullInjectResolverChainItem nullInjectResolverChainItem = new NullInjectResolverChainItem();
        SpecialValueObjectResolverChainItem specialValueObjectResolverChainItem = new SpecialValueObjectResolverChainItem();

        DependencyObjectResolverHandler dependencyObjectResolverHandler = new DependencyObjectResolverHandler(
                Arrays.asList(dependentObjectResolverChainItem, propertyObjectResolverChainItem, dependencyCollectionResolverChainItem,
                        nullInjectResolverChainItem, specialValueObjectResolverChainItem));

        ConstructorInvoker constructorInvoker = new ConstructorInvoker(dependencyObjectResolverHandler);
        MethodInvoker methodInvoker = new MethodInvoker(dependencyObjectResolverHandler);
        SetterInvoker setterInvoker = new SetterInvoker(methodInvoker);
        FieldSetInvoker fieldSetInvoker = new FieldSetInvoker(dependencyObjectResolverHandler);

        postConstructInvoker = new PostConstructInvoker();
        autowirePostProcessSupport = new AutowirePostProcessSupport(setterInvoker, fieldSetInvoker);

        InjectDescriptorsToDependencies injectDescriptorsToDependencies = new InjectDescriptorsToDependencies();

        stereotypeAnnotatedBeanFactoryChainItem = new StereotypeAnnotatedBeanFactoryChainItem(
                constructorInvoker, autowirePostProcessSupport, injectDescriptorsToDependencies);
        manualBeanFactoryChainItem = new ManualBeanFactoryChainItem();
        configurationBeanFacotoryChainItem = new ConfigurationBeanFacotoryChainItem(
                methodInvoker, injectDescriptorsToDependencies);

        this.environmentAwareInjector = new EnvironmentAwareInjector();
        this.beanNameAwareInjector = new BeanNameAwareInjector();
        this.contextAwareInjector = new ContextAwareInjector();
        this.interfaceAwareInjector = new InterfaceAwareInjector(
                asList(environmentAwareInjector, beanNameAwareInjector, contextAwareInjector));
        beanPostConstructInitializer = new BeanPostConstructInitializer(postConstructInvoker, interfaceAwareInjector);
    }

    public BeanFactory createBeanFactory() {
        return new BeanFactory(
                asList(stereotypeAnnotatedBeanFactoryChainItem, configurationBeanFacotoryChainItem, manualBeanFactoryChainItem),
                beanPostConstructInitializer);
    }

    public AutowirePostProcessSupport getAutowirePostProcessSupport() {
        return autowirePostProcessSupport;
    }

    public BeanPostConstructInitializer getBeanPostConstructInitializer() {
        return beanPostConstructInitializer;
    }

}

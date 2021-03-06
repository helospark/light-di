package com.helospark.lightdi.internal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.beanfactory.chain.BeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.BeanPostConstructInitializer;
import com.helospark.lightdi.beanfactory.chain.ConfigurationBeanFacotoryChainItem;
import com.helospark.lightdi.beanfactory.chain.ManualBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.StereotypeAnnotatedBeanFactoryChainItem;
import com.helospark.lightdi.beanfactory.chain.support.AutowirePostProcessSupport;
import com.helospark.lightdi.beanfactory.chain.support.InjectDescriptorsToDependencies;
import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractor;
import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractorFactory;
import com.helospark.lightdi.conditional.ConditionalFilter;
import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.definitioncollector.BeanDefinitionCollectorChainItem;
import com.helospark.lightdi.definitioncollector.ConfigurationClassBeanDefinitionCollectorChainItem;
import com.helospark.lightdi.definitioncollector.ImportBeanDefinitionCollectorChainItem;
import com.helospark.lightdi.definitioncollector.StereotypeBeanDefinitionCollectorChainItem;
import com.helospark.lightdi.dependencywire.ComponentScanCollector;
import com.helospark.lightdi.dependencywire.DefinitionIntegrityChecker;
import com.helospark.lightdi.dependencywire.FindInDependencySupport;
import com.helospark.lightdi.dependencywire.PropertyDescriptorFactory;
import com.helospark.lightdi.dependencywire.RecursiveDependencyDescriptorCollector;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.dependencywire.chain.BeanDependencyWireChainItem;
import com.helospark.lightdi.dependencywire.chain.CommonDependencyWireChain;
import com.helospark.lightdi.dependencywire.chain.ComponentDependencyWireChainItem;
import com.helospark.lightdi.dependencywire.chain.DependencyWireChain;
import com.helospark.lightdi.dependencywire.chain.support.CompatibleParameterFactory;
import com.helospark.lightdi.dependencywire.chain.support.ConstructorWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.DependencyDescriptorBuilder;
import com.helospark.lightdi.dependencywire.chain.support.FieldWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.MethodDependencyCollector;
import com.helospark.lightdi.dependencywire.chain.support.SetterWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.chain.CollectionInjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.DependencyInjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.SpecialValueDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.ValueInjectDescriptorBuilderChainItem;
import com.helospark.lightdi.properties.AssignablePredicate;
import com.helospark.lightdi.properties.EnvironmentFactory;
import com.helospark.lightdi.properties.EnvironmentInitializerFactory;
import com.helospark.lightdi.properties.PropertiesFileLoader;
import com.helospark.lightdi.properties.PropertyStringResolver;
import com.helospark.lightdi.properties.PropertyValueResolver;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.MethodInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;
import com.helospark.lightdi.reflection.aware.AwareDependencyInjectorChainItem;
import com.helospark.lightdi.reflection.aware.InterfaceAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.BeanNameAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.ContextAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.EnvironmentAwareInjector;
import com.helospark.lightdi.reflection.aware.chain.ImportingClassAwareInjector;
import com.helospark.lightdi.reflection.chain.DependencyCollectionResolverChainItem;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.reflection.chain.DependentObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.NullInjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.SpecialValueObjectResolverChainItem;
import com.helospark.lightdi.scanner.ClasspathProvider;
import com.helospark.lightdi.scanner.ClasspathScannerChain;
import com.helospark.lightdi.scanner.FastClasspathScannerChainItem;
import com.helospark.lightdi.scanner.PreprocessedAnnotationScannerChainItem;
import com.helospark.lightdi.scanner.PreprocessedFileLocationProvider;
import com.helospark.lightdi.util.AutowirePostProcessorFactory;
import com.helospark.lightdi.util.CollectionFactory;
import com.helospark.lightdi.util.DependencyChooser;

/**
 * Initializes the internal DI framework.
 * @author helospark
 */
public class InternalDiInitializer {
    private InternalDi internalDi;
    private static final Logger LOGGER = LoggerFactory.getLogger(InternalDiInitializer.class);

    public InternalDiInitializer(InternalDi internalDi) {
        this.internalDi = internalDi;
    }

    public void initialize(LightDiContextConfiguration lightDiContextConfiguration) {
        LOGGER.debug("Internal DI init started");
        addDependency(lightDiContextConfiguration);
        addDependency(new StreamFactory(lightDiContextConfiguration));

        addDependency(new DependencyChooser(getDependency(StreamFactory.class)));

        prepareEnvironment();
        prepareBeanDefinitionCollector();
        prepareBeanFactory();
        prepareWiringProcessor();
        prepareDependencyCollector(lightDiContextConfiguration);

        addDependency(new ConditionalFilter());
        addDependency(new DefinitionIntegrityChecker(lightDiContextConfiguration, getDependency(BeanFactory.class)));

        LOGGER.debug("Internal DI init ended");
    }

    // @formatter:off

    private void prepareEnvironment() {
        addDependency(new PropertyValueResolver());
        addDependency(new PropertyStringResolver(getDependency(PropertyValueResolver.class)));
        addDependency(new AssignablePredicate());
        addDependency(new CollectionFactory());
        addDependency(new ValueResolver(getDependency(PropertyStringResolver.class),
                getDependency(AssignablePredicate.class),
                getDependency(CollectionFactory.class)));
        addDependency(new EnvironmentFactory(getDependency(ValueResolver.class)));
    }

    private void prepareBeanDefinitionCollector() {
        addDependency(new ConditionalAnnotationsExtractorFactory().createConditionalAnnotationsExtractor());
        addDependency(new StereotypeBeanDefinitionCollectorChainItem(getDependency(ConditionalAnnotationsExtractor.class)));
        addDependency(new ConfigurationClassBeanDefinitionCollectorChainItem(getDependency(ConditionalAnnotationsExtractor.class)));
        addDependency(new ImportBeanDefinitionCollectorChainItem(getDependency(ConfigurationClassBeanDefinitionCollectorChainItem.class)));
        addDependency(new BeanDefinitionCollector(getDependencyList(BeanDefinitionCollectorChainItem.class), getDependency(StreamFactory.class)));
    }

    private void prepareBeanFactory() {
        addDependency(new DependentObjectResolverChainItem());
        addDependency(new PropertyObjectResolverChainItem());
        addDependency(new CollectionFactory());
        addDependency(new DependencyCollectionResolverChainItem(getDependency(CollectionFactory.class)));
        addDependency(new NullInjectResolverChainItem());
        addDependency(new SpecialValueObjectResolverChainItem());

        addDependency(new DependencyObjectResolverHandler(
                getDependencyList(DependencyObjectResolverChainItem.class)));

        addDependency(new ConstructorInvoker(getDependency(DependencyObjectResolverHandler.class)));
        addDependency(new MethodInvoker(getDependency(DependencyObjectResolverHandler.class)));
        addDependency(new SetterInvoker(getDependency(MethodInvoker.class)));
        addDependency(new FieldSetInvoker(getDependency(DependencyObjectResolverHandler.class)));

        addDependency(new PostConstructInvoker());
        addDependency(new AutowirePostProcessSupport(getDependency(SetterInvoker.class), getDependency(FieldSetInvoker.class)));

        addDependency(new InjectDescriptorsToDependencies());

        addDependency(new StereotypeAnnotatedBeanFactoryChainItem(getDependency(ConstructorInvoker.class), getDependency(InjectDescriptorsToDependencies.class)));
        addDependency(new ManualBeanFactoryChainItem());
        addDependency(new ConfigurationBeanFacotoryChainItem(getDependency(MethodInvoker.class), getDependency(InjectDescriptorsToDependencies.class)));

        addDependency(new EnvironmentAwareInjector());
        addDependency(new BeanNameAwareInjector());
        addDependency(new ContextAwareInjector());
        addDependency(new ImportingClassAwareInjector());
        addDependency(new InterfaceAwareInjector(getDependencyList(AwareDependencyInjectorChainItem.class)));

        addDependency(new BeanPostConstructInitializer(getDependency(PostConstructInvoker.class), getDependency(InterfaceAwareInjector.class)));

        addDependency(new BeanFactory(
                getDependencyList(BeanFactoryChainItem.class),
                getDependency(BeanPostConstructInitializer.class),
                getDependency(StreamFactory.class),
                getDependency(AutowirePostProcessSupport.class)));
    }

    private void prepareWiringProcessor() {
        addDependency(new FindInDependencySupport(getDependency(DependencyChooser.class)));
        addDependency(new PropertyDescriptorFactory());

        addDependency(new CollectionInjectDescriptorBuilderChainItem(getDependency(FindInDependencySupport.class)));
        addDependency(new DependencyInjectDescriptorBuilderChainItem(getDependency(FindInDependencySupport.class)));
        addDependency(new ValueInjectDescriptorBuilderChainItem(getDependency(PropertyDescriptorFactory.class)));
        addDependency(new SpecialValueDescriptorBuilderChainItem());

        addDependency(new DependencyDescriptorBuilder(
                getDependencyList(getDependency(ValueInjectDescriptorBuilderChainItem.class),
                        getDependency(SpecialValueDescriptorBuilderChainItem.class),
                        getDependency(CollectionInjectDescriptorBuilderChainItem.class),
                        getDependency(DependencyInjectDescriptorBuilderChainItem.class))));

        addDependency(new CompatibleParameterFactory());

        addDependency(new ConstructorWireSupport(getDependency(DependencyDescriptorBuilder.class), getDependency(CompatibleParameterFactory.class)));

        addDependency(new MethodDependencyCollector(
                getDependency(DependencyDescriptorBuilder.class), getDependency(CompatibleParameterFactory.class)));

        addDependency(new SetterWireSupport(getDependency(PropertyDescriptorFactory.class), getDependency(MethodDependencyCollector.class)));
        addDependency(new FieldWireSupport(getDependency(DependencyDescriptorBuilder.class)));

        addDependency(new BeanDependencyWireChainItem(getDependency(MethodDependencyCollector.class)));
        addDependency(new ComponentDependencyWireChainItem(getDependency(ConstructorWireSupport.class)));
        addDependency(new CommonDependencyWireChain(getDependency(SetterWireSupport.class), getDependency(FieldWireSupport.class)));

        addDependency(new WiringProcessingService(getDependencyList(DependencyWireChain.class), getDependency(StreamFactory.class)));

        addDependency(new AutowirePostProcessorFactory(getDependency(WiringProcessingService.class),
                getDependency(AutowirePostProcessSupport.class), getDependency(BeanPostConstructInitializer.class)));
    }

    private void prepareDependencyCollector(LightDiContextConfiguration lightDiContextConfiguration) {
        addDependency(new PropertiesFileLoader());
        addDependency(new EnvironmentInitializerFactory(getDependency(PropertiesFileLoader.class)));
        addDependency(new ComponentScanCollector(getDependency(StreamFactory.class)));

        prepareClasspathScanner(lightDiContextConfiguration);

        addDependency(new RecursiveDependencyDescriptorCollector(getDependency(ClasspathScannerChain.class),
                getDependency(BeanDefinitionCollector.class), getDependency(ComponentScanCollector.class)));
    }

    private void prepareClasspathScanner(LightDiContextConfiguration lightDiContextConfiguration) {
        addDependency(new ClasspathProvider());
        addDependency(new PreprocessedFileLocationProvider(getDependency(ClasspathProvider.class), getDependency(StreamFactory.class)));
        addDependency(new PreprocessedAnnotationScannerChainItem(getDependency(PreprocessedFileLocationProvider.class), getDependency(StreamFactory.class), lightDiContextConfiguration));
        addDependency(new FastClasspathScannerChainItem());

        addDependency(new ClasspathScannerChain(
                getDependencyList(getDependency(PreprocessedAnnotationScannerChainItem.class),
                        getDependency(FastClasspathScannerChainItem.class))));
    }

    // @formatter:on

    private void addDependency(Object dependency) {
        internalDi.addDependency(dependency);
    }

    private <T> T getDependency(Class<T> clazz) {
        return internalDi.getDependency(clazz);
    }

    private <T> List<T> getDependencyList(Class<T> classToFind) {
        return internalDi.getDependencyList(classToFind);
    }

    private <T> List<T> getDependencyList(T... objects) {
        return internalDi.getDependencyList(objects);
    }

}

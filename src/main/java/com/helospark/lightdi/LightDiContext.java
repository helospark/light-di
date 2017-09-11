package com.helospark.lightdi;

import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;
import static com.helospark.lightdi.util.DependencyChooser.findPrimary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.beanfactory.BeanFactoryFactory;
import com.helospark.lightdi.conditional.ConditionalFilter;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.definitioncollector.BeanDefinitionProcessorChainFactory;
import com.helospark.lightdi.dependencywire.ComponentScanCollector;
import com.helospark.lightdi.dependencywire.RecursiveDependencyDescriptorCollector;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.dependencywire.WiringProcessingServiceFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.postprocessor.BeanPostProcessor;
import com.helospark.lightdi.properties.Environment;
import com.helospark.lightdi.properties.EnvironmentFactory;
import com.helospark.lightdi.properties.EnvironmentInitializerFactory;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.scanner.LightDiClasspathScanner;
import com.helospark.lightdi.util.AutowirePostProcessor;
import com.helospark.lightdi.util.DependencyChooser;

public class LightDiContext implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LightDi.class);
    private static boolean CHECK_FOR_INTEGRITY = true;

    private BeanFactory beanFactory;
    private WiringProcessingService wiringProcessingService;
    private BeanDefinitionCollector beanDefinitionCollector;
    private EnvironmentInitializerFactory environmentInitializer;
    private RecursiveDependencyDescriptorCollector recursiveDependencyDescriptorCollector;
    private LightDiClasspathScanner lightDiClasspathScanner;

    private WiringProcessingServiceFactory preprocessWireServiceFactory;
    private BeanFactoryFactory beanFactoryFactory;
    private BeanDefinitionProcessorChainFactory beanDefinitionProcessorChainFactory;

    private Map<DependencyDescriptor, Object> initializedSingletonBeans;
    private Map<DependencyDescriptor, Object> initializedPrototypeBeans;
    private List<BeanPostProcessor> beanPostProcessors;
    private ValueResolver valueResolver;
    private AutowirePostProcessor autowireSupportUtil;
    private List<DependencyDescriptor> dependencyDescriptors;

    private Environment environment = null;

    private ConditionalFilter conditionalFilter;

    public LightDiContext() {
        this.initializedSingletonBeans = new HashMap<>();
        this.initializedPrototypeBeans = new HashMap<>();
        this.dependencyDescriptors = new ArrayList<>();
        this.beanPostProcessors = new ArrayList<>();
        this.environment = new EnvironmentFactory().createEnvironment(this);
        this.conditionalFilter = new ConditionalFilter();

        /** Moved from LightDi class **/
        lightDiClasspathScanner = new LightDiClasspathScanner();

        beanDefinitionProcessorChainFactory = new BeanDefinitionProcessorChainFactory();
        beanDefinitionCollector = beanDefinitionProcessorChainFactory.createBeanDefinitionProcessorChain();

        beanFactoryFactory = new BeanFactoryFactory();
        beanFactory = beanFactoryFactory.createBeanFactory();

        preprocessWireServiceFactory = new WiringProcessingServiceFactory();
        wiringProcessingService = preprocessWireServiceFactory.createFieldWireSupport();

        environmentInitializer = new EnvironmentInitializerFactory();

        ComponentScanCollector componentScanCollector = new ComponentScanCollector();

        recursiveDependencyDescriptorCollector = new RecursiveDependencyDescriptorCollector(lightDiClasspathScanner, beanDefinitionCollector,
                componentScanCollector);

        /** Register stuff */
        registerSingleton(environment);
        registerSingleton(this);
    }

    public <T> T getBean(Class<T> clazz) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(clazz)
                .build();
        return (T) getOrCreateDependencyInternal(query);
    }

    public Object getBean(DependencyDescriptorQuery query) {
        return getOrCreateDependencyInternal(query);
    }

    public Object getBean(DependencyDescriptor query) {
        return getOrCreateDependencyInternal(convertToQuery(query));
    }

    public Object getBean(String qualifier) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withQualifier(qualifier)
                .build();
        return getOrCreateDependencyInternal(query);
    }

    public ValueResolver getValueResolver() {
        return valueResolver;
    }

    public void eagerInitAllBeans() {
        dependencyDescriptors.stream()
                .forEach(dependencyDescriptor -> getBean(convertToQuery(dependencyDescriptor)));
    }

    public void registerSingleton(Object bean) {
        this.registerSingleton(bean, createBeanNameForStereotypeAnnotatedClass(bean.getClass()));
    }

    public void registerSingleton(Object bean, String beanName) {
        ManualDependencyDescriptor dependencyDescriptor = ManualDependencyDescriptor.builder()
                .withClazz(bean.getClass())
                .withIsLazy(false)
                .withQualifier(beanName)
                .withScope(LightDiConstants.SCOPE_SINGLETON)
                .withIsPrimary(false)
                .build();
        registerSingleton(dependencyDescriptor, bean);
    }

    public void registerSingleton(ManualDependencyDescriptor dependencyDescriptor, Object bean) {
        dependencyDescriptor.setScope(LightDiConstants.SCOPE_SINGLETON);
        this.dependencyDescriptors.add(dependencyDescriptor);
        this.initializedSingletonBeans.put(dependencyDescriptor, bean);
    }

    private Object getOrCreateDependencyInternal(DependencyDescriptorQuery query) {
        Optional<DependencyDescriptor> initializedSingletonDescriptor = findInitializedSingletonDescriptor(query);
        if (initializedSingletonDescriptor.isPresent()) {
            return initializedSingletonBeans.get(initializedSingletonDescriptor.get());
        } else {
            return createNewInstance(query);
        }
    }

    private Object createNewInstance(DependencyDescriptorQuery query) {
        DependencyDescriptor foundDependency = DependencyChooser.findDependencyFromQuery(dependencyDescriptors, query);
        return createDependency(foundDependency);
    }

    private Object createDependency(DependencyDescriptor descriptorToUse) {
        Object createdBean = beanFactory.createBean(this, descriptorToUse);
        createdBean = postProcessInstance(createdBean, descriptorToUse);
        if (descriptorToUse.getScope().equals(LightDiConstants.SCOPE_SINGLETON)) {
            initializedSingletonBeans.put(descriptorToUse, createdBean);
        } else {
            initializedPrototypeBeans.put(descriptorToUse, createdBean);
        }
        return createdBean;
    }

    private Object postProcessInstance(Object createdBean, DependencyDescriptor descriptorToUse) {
        Object result = createdBean;

        // BeanPostProcessors try to run on BeanPostProcessor and it's dependencies
        if (beanPostProcessors != null) {
            for (BeanPostProcessor postProcessor : beanPostProcessors) {
                result = postProcessor.postProcessAfterInitialization(result, descriptorToUse);
            }
        }
        return result;
    }

    private Optional<DependencyDescriptor> findInitializedSingletonDescriptor(DependencyDescriptorQuery toFind) {
        List<DependencyDescriptor> foundDependencies = DependencyChooser.findDependencyDescriptor(initializedSingletonBeans.keySet(),
                toFind);
        if (foundDependencies.isEmpty()) {
            return Optional.empty();
        } else if (foundDependencies.size() == 1) {
            return Optional.of(foundDependencies.get(0));
        } else {
            return findPrimary(foundDependencies);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getListOfBeans(Class<T> clazz) {
        List<Object> result = dependencyDescriptors.stream()
                .filter(descriptor -> clazz.isAssignableFrom(descriptor.getClazz()))
                .map(descriptor -> convertToQuery(descriptor))
                .map(descriptor -> this.getBean(descriptor))
                .collect(Collectors.toList());
        return (List<T>) result;

    }

    private DependencyDescriptorQuery convertToQuery(DependencyDescriptor descriptor) {
        return DependencyDescriptorQuery.builder()
                .withClazz(descriptor.getClazz())
                .withQualifier(descriptor.getQualifier())
                .build();
    }

    @Override
    public void close() throws Exception {
        initializedSingletonBeans.entrySet()
                .stream()
                .forEach(entry -> closeDependency(entry));
        initializedPrototypeBeans.entrySet()
                .stream()
                .forEach(entry -> closeDependency(entry));
    }

    private void closeDependency(Entry<DependencyDescriptor, Object> entry) {
        entry.getKey()
                .getPreDestroyMethods()
                .stream()
                .forEach(preDestoryMethod -> invokeMethod(preDestoryMethod, entry.getValue()));
    }

    private void invokeMethod(Method preDestoryMethod, Object object) {
        try {
            preDestoryMethod.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Exception thrown while calling preDestroy method", e);
        }
    }

    public List<DependencyDescriptor> getDependencyDescriptors() {
        return dependencyDescriptors;
    }

    public AutowirePostProcessor getAutowireSupportUtil() {
        return autowireSupportUtil;
    }

    public void setAutowireSupportUtil(AutowirePostProcessor autowireSupportUtil) {
        this.autowireSupportUtil = autowireSupportUtil;
    }

    public void loadDependencies(String packageName) {
        try {
            List<DependencyDescriptor> loadedDescriptors = recursiveDependencyDescriptorCollector.collectDependencies(packageName);

            environment = environmentInitializer.initializeEnvironment(environment, loadedDescriptors);

            loadedDescriptors = conditionalFilter.filterDependencies(this, loadedDescriptors);

            dependencyDescriptors.addAll(loadedDescriptors);

            wiringProcessingService.wireTogetherDependencies(dependencyDescriptors);

            autowireSupportUtil = new AutowirePostProcessor(
                    beanDefinitionProcessorChainFactory.getStereotypeBeanDefinitionCollectorChainItem(),
                    preprocessWireServiceFactory.createFieldWireSupport(), beanFactoryFactory.getAutowirePostProcessSupport(),
                    this);

            if (CHECK_FOR_INTEGRITY) {
                beanFactory.assertValidConfiguration(dependencyDescriptors);
            }

            initializeEagerDependencies(dependencyDescriptors);

            LOGGER.info("Context initialized");

            this.beanPostProcessors.addAll(this.getListOfBeans(BeanPostProcessor.class));

        } catch (Exception e) {
            throw new ContextInitializationFailedException("Context initialization failed", e);
        }
    }

    private void initializeEagerDependencies(List<DependencyDescriptor> dependencyDescriptors) {
        dependencyDescriptors.stream()
                .filter(dependency -> !dependency.isLazy())
                .forEach(dependency -> getBean(dependency));
    }

    public Environment getEnvironment() {
        return environment;
    }

}

package com.helospark.lightdi;

import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;
import static com.helospark.lightdi.util.DependencyChooser.findPrimary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.conditional.ConditionalFilter;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.dependencywire.DefinitionIntegrityChecker;
import com.helospark.lightdi.dependencywire.RecursiveDependencyDescriptorCollector;
import com.helospark.lightdi.dependencywire.WiringProcessingService;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.internal.DefaultInternalDi;
import com.helospark.lightdi.postprocessor.BeanPostProcessor;
import com.helospark.lightdi.properties.Environment;
import com.helospark.lightdi.properties.EnvironmentFactory;
import com.helospark.lightdi.properties.EnvironmentInitializerFactory;
import com.helospark.lightdi.properties.PropertySourceHolder;
import com.helospark.lightdi.properties.converter.BooleanPropertyConverter;
import com.helospark.lightdi.properties.converter.IntegerPropertyConverter;
import com.helospark.lightdi.properties.converter.StringPropertyConverter;
import com.helospark.lightdi.util.AutowirePostProcessor;
import com.helospark.lightdi.util.AutowirePostProcessorFactory;
import com.helospark.lightdi.util.DependencyChooser;

/**
 * Contains all data required for the context, like beans and properties.
 * 
 * @author helospark
 */
public class LightDiContext implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LightDi.class);

    // Helpers
    private DefaultInternalDi internalDi;
    private BeanFactory beanFactory;
    private WiringProcessingService wiringProcessingService;
    private EnvironmentInitializerFactory environmentInitializer;
    private RecursiveDependencyDescriptorCollector recursiveDependencyDescriptorCollector;
    private AutowirePostProcessor autowireSupportUtil;
    private ConditionalFilter conditionalFilter;
    private DefinitionIntegrityChecker definitionIntegrityChecker;
    private AutowirePostProcessorFactory autowirePostProcessorFactory;

    // State
    private SortedSet<DependencyDescriptor> dependencyDescriptors;
    private Map<DependencyDescriptor, Object> initializedSingletonBeans;
    private Map<DependencyDescriptor, Object> initializedPrototypeBeans;
    private Set<BeanPostProcessor> beanPostProcessors;
    private Environment environment = null;

    /**
     * Creates new context with default configuration.
     */
    public LightDiContext() {
        this(LightDiContextConfiguration.builder()
                .withCheckForIntegrity(true)
                .withThreadNumber(1)
                .build());
    }

    /**
     * Creates new context with given configuration.
     * 
     * @param lightDiContextConfiguration configuration
     */
    public LightDiContext(LightDiContextConfiguration lightDiContextConfiguration) {
        this.initializedSingletonBeans = new HashMap<>();
        this.initializedPrototypeBeans = new HashMap<>();
        this.dependencyDescriptors = new TreeSet<>();
        this.beanPostProcessors = new LinkedHashSet<>(); // TODO: We may need to order bean post processors better

        internalDi = new DefaultInternalDi();
        internalDi.initialize(lightDiContextConfiguration);

        this.conditionalFilter = internalDi.getDependency(ConditionalFilter.class);
        this.environment = internalDi.getDependency(EnvironmentFactory.class).createEnvironment(this);
        this.beanFactory = internalDi.getDependency(BeanFactory.class);
        this.wiringProcessingService = internalDi.getDependency(WiringProcessingService.class);
        this.environmentInitializer = internalDi.getDependency(EnvironmentInitializerFactory.class);
        this.recursiveDependencyDescriptorCollector = internalDi
                .getDependency(RecursiveDependencyDescriptorCollector.class);
        this.definitionIntegrityChecker = internalDi.getDependency(DefinitionIntegrityChecker.class);
        this.autowirePostProcessorFactory = internalDi.getDependency(AutowirePostProcessorFactory.class);

        /** Register default beans */
        registerSingleton(environment);
        registerSingleton(this);
        registerSingleton(new IntegerPropertyConverter());
        registerSingleton(new StringPropertyConverter());
        registerSingleton(new BooleanPropertyConverter());
    }

    /**
     * Get a bean from the context by type.
     * 
     * @param clazz bean type to get
     * @return found and instantiated bean, not null
     * @throws IllegalArgumentException if the given bean cannot be found
     * @param <T> type of bean
     */
    public <T> T getBean(Class<T> clazz) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder().withClazz(clazz).build();
        return (T) getOrCreateDependencyInternal(query);
    }

    /**
     * Get bean by the given Query descriptor.
     * 
     * @param query to get bean based on
     * @return found and instantiated bean. May be null if the query is not required
     * @throws IllegalArgumentException if the given bean cannot be found
     */
    public Object getBean(DependencyDescriptorQuery query) {
        return getOrCreateDependencyInternal(query);
    }

    /**
     * Get bean by the given descriptor.
     * 
     * @param query to get bean based on
     * @return found and instantiated bean. May be null if the query is not required
     * @throws IllegalArgumentException if the given bean cannot be found
     */
    public Object getBean(DependencyDescriptor query) {
        return getOrCreateDependencyInternal(convertToQuery(query));
    }

    /**
     * Get bean by bean name.
     * 
     * @param qualifier bean name
     * @return found and instantiated bean. Never null
     * @throws IllegalArgumentException if the given bean cannot be found
     */
    public Object getBean(String qualifier) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder().withQualifier(qualifier).build();
        return getOrCreateDependencyInternal(query);
    }

    /**
     * Eagerly initialize all beans in the context.
     */
    public void eagerInitAllBeans() {
        dependencyDescriptors.stream().forEach(dependencyDescriptor -> getBean(convertToQuery(dependencyDescriptor)));
    }

    /**
     * Register a bean manually. Bean name is automatically generated by the class
     * of the bean.
     * 
     * @param bean instance to register
     */
    public void registerSingleton(Object bean) {
        this.registerSingleton(bean, createBeanNameForStereotypeAnnotatedClass(bean.getClass()));
    }

    /**
     * Register a bean manually.
     * 
     * @param bean     instance to register
     * @param beanName name of the bean
     */
    public void registerSingleton(Object bean, String beanName) {
        ManualDependencyDescriptor dependencyDescriptor = ManualDependencyDescriptor.builder()
                .withClazz(bean.getClass()).withIsLazy(false).withQualifier(beanName)
                .withScope(LightDiConstants.SCOPE_SINGLETON).withIsPrimary(false).build();
        registerSingleton(dependencyDescriptor, bean);
    }

    /**
     * Register a bean manually.
     * 
     * @param dependencyDescriptor to describe the bean data
     * @param bean                 instance to register
     */
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
        if (foundDependency == null) {
            return null;
        } else {
            return createDependency(foundDependency);
        }
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
        SortedSet<DependencyDescriptor> foundDependencies = DependencyChooser
                .findDependencyDescriptor(initializedSingletonBeans.keySet(), toFind);
        if (foundDependencies.isEmpty()) {
            return Optional.empty();
        } else if (foundDependencies.size() == 1) {
            return Optional.of(foundDependencies.first());
        } else {
            return findPrimary(foundDependencies);
        }
    }

    /**
     * Return list of all beans with the given class.
     * 
     * @param clazz to get beans from
     * @return list of beans
     * @param <T> type of bean
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getListOfBeans(Class<T> clazz) {
        List<Object> result = dependencyDescriptors.stream()
                .filter(descriptor -> clazz.isAssignableFrom(descriptor.getClazz()))
                .map(descriptor -> convertToQuery(descriptor)).map(descriptor -> this.getBean(descriptor))
                .collect(Collectors.toList());
        return (List<T>) result;

    }

    private DependencyDescriptorQuery convertToQuery(DependencyDescriptor descriptor) {
        return DependencyDescriptorQuery.builder().withClazz(descriptor.getClazz())
                .withQualifier(descriptor.getQualifier()).build();
    }

    /**
     * Close the context.
     * <p>
     * May throw exception if the PreDestroy methods throw.
     */
    @Override
    public void close() {
        initializedSingletonBeans.entrySet().stream().forEach(entry -> closeDependency(entry));
        initializedPrototypeBeans.entrySet().stream().forEach(entry -> closeDependency(entry));
    }

    private void closeDependency(Entry<DependencyDescriptor, Object> entry) {
        entry.getKey().getPreDestroyMethods().stream()
                .forEach(preDestoryMethod -> invokeMethod(preDestoryMethod, entry.getValue()));
    }

    private void invokeMethod(Method preDestoryMethod, Object object) {
        try {
            preDestoryMethod.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Exception thrown while calling preDestroy method", e);
        }
    }

    /**
     * Returns all dependency descriptors.
     * 
     * @return all dependency descriptors
     */
    public SortedSet<DependencyDescriptor> getDependencyDescriptors() {
        return dependencyDescriptors;
    }

    /**
     * Get autowire support util.
     * <p>
     * Can be used to inject fields to an nonmanaged instance.
     * 
     * @return autowire support util
     */
    public AutowirePostProcessor getAutowireSupportUtil() {
        return autowireSupportUtil;
    }

    /**
     * Autowire fields to the given instance, even if the given instance is not
     * managed.
     * 
     * @param instance to autowire fields to
     */
    public void processAutowireTo(Object instance) {
        this.autowireSupportUtil.autowireFieldsTo(instance);
    }

    /**
     * Load all beans in the given package. It will find annotated beans by
     * classpath scan.
     * <p>
     * This will add beans, refresh context.<br>
     * Note that calling this method separately for two different packages, which
     * depend on each other will fail on the first call with not found beans. In
     * that case use loadDependencies method. It may be called any time, even after
     * an already started context.
     * 
     * @param packageName package name to scan
     */
    public void loadDependencyFromPackage(String packageName) {
        loadDependencies(Collections.singletonList(packageName), Collections.emptyList());
    }

    /**
     * Load given annotated bean (ex. Component).
     * <p>
     * This will add beans, refresh context.<br>
     * Note that calling this method separately for two different configuration,
     * which depend on each other will fail on the first call with not found beans.
     * In that case use loadDependencies method.<br>
     * It may be called any time, even after an already started context.
     * 
     * @param clazz annotated class
     */
    public void loadDependenciesFromClass(Class<?> clazz) {
        loadDependencies(Collections.emptyList(), Collections.singletonList(clazz));
    }

    /**
     * Load dependencies from given packages and annotated class.
     * 
     * @param packages to scan and load beans from
     * @param classes  annotated classes to load as beans
     */
    public void loadDependencies(List<String> packages, List<Class<?>> classes) {
        try {
            SortedSet<DependencyDescriptor> loadedDescriptors = new TreeSet<>();
            loadedDescriptors
                    .addAll(recursiveDependencyDescriptorCollector.collectDependenciesStartingFromClass(classes));
            loadedDescriptors
                    .addAll(recursiveDependencyDescriptorCollector.collectDependenciesUsingFullClasspathScan(packages));
            processDescriptors(loadedDescriptors);
        } catch (Exception e) {
            throw new ContextInitializationFailedException("Context initialization failed", e);
        }
    }

    private void processDescriptors(SortedSet<DependencyDescriptor> loadedDescriptors) {
        environment = environmentInitializer.initializeEnvironment(environment, loadedDescriptors);
        loadedDescriptors = conditionalFilter.filterDependencies(this, loadedDescriptors);

        dependencyDescriptors.addAll(loadedDescriptors);

        wiringProcessingService.wireTogetherDependencies(dependencyDescriptors);

        definitionIntegrityChecker.checkIntegrityIfNeeded(loadedDescriptors);

        initializeEagerDependencies(dependencyDescriptors);

        this.beanPostProcessors.addAll(this.getListOfBeans(BeanPostProcessor.class));
        this.autowireSupportUtil = autowirePostProcessorFactory.create(this);

        LOGGER.debug("Context initialized");
    }

    private void initializeEagerDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors) {
        dependencyDescriptors.stream().filter(dependency -> !dependency.isLazy())
                .forEach(dependency -> getBean(dependency));
    }

    /**
     * Get the environment. It can be used to add properties.
     * 
     * @return environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Add a custom property source.
     * 
     * @param propertySourceHolder custom property source
     */
    public void addPropertySource(PropertySourceHolder propertySourceHolder) {
        environment.addPropertySource(propertySourceHolder);
    }
}

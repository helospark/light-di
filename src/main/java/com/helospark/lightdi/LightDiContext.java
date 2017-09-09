package com.helospark.lightdi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.beanfactory.BeanFactory;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.postprocessor.BeanPostProcessor;
import com.helospark.lightdi.properties.ValueResolver;
import com.helospark.lightdi.util.AutowirePostProcessor;

public class LightDiContext implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LightDiContext.class);
    private Map<DependencyDescriptor, Object> initializedSingletonBeans;
    private Map<DependencyDescriptor, Object> initializedPrototypeBeans;
    private List<BeanPostProcessor> beanPostProcessors;
    private BeanFactory beanFactory;
    private ValueResolver valueResolver;
    private AutowirePostProcessor autowireSupportUtil;
    private List<DependencyDescriptor> dependencyDescriptors;

    public LightDiContext(List<DependencyDescriptor> dependencyDescriptors, ValueResolver valueResolver,
            BeanFactory beanFactory) {
        this.initializedSingletonBeans = new HashMap<>();
        this.initializedPrototypeBeans = new HashMap<>();
        this.dependencyDescriptors = dependencyDescriptors;
        this.beanFactory = beanFactory;
        this.valueResolver = valueResolver;
        this.beanPostProcessors = this.getListOfBeans(BeanPostProcessor.class);
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

    private Object getOrCreateDependencyInternal(DependencyDescriptorQuery query) {
        Optional<DependencyDescriptor> initializedSingletonDescriptor = findInitializedSingletonDescriptor(query);
        if (initializedSingletonDescriptor.isPresent()) {
            return initializedSingletonBeans.get(initializedSingletonDescriptor.get());
        } else {
            return createNewInstance(query);
        }
    }

    private Object createNewInstance(DependencyDescriptorQuery query) {
        List<DependencyDescriptor> dependencyToCreate = findDependencyDescriptor(dependencyDescriptors, query);
        Optional<DependencyDescriptor> primary = findPrimary(dependencyToCreate);
        if (dependencyToCreate.size() == 1) {
            return createDependency(dependencyToCreate.get(0));
        } else if (primary.isPresent()) {
            return createDependency(primary.get());
        } else {
            throw new IllegalArgumentException("No single match for " + query + " found " + dependencyToCreate);
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
        List<DependencyDescriptor> foundDependencies = findDependencyDescriptor(initializedSingletonBeans.keySet(),
                toFind);
        if (foundDependencies.isEmpty()) {
            return Optional.empty();
        } else if (foundDependencies.size() == 1) {
            return Optional.of(foundDependencies.get(0));
        } else {
            return findPrimary(foundDependencies);
        }
    }

    private List<DependencyDescriptor> findDependencyDescriptor(Collection<DependencyDescriptor> dependencies,
            DependencyDescriptorQuery toFind) {
        return dependencies
                .stream()
                .filter(dependencyEntry -> dependencyEntry.doesMatch(toFind))
                .collect(Collectors.toList());
    }

    private Optional<DependencyDescriptor> findPrimary(
            List<DependencyDescriptor> foundDependencies) {
        return foundDependencies.stream()
                .filter(dependency -> dependency.isPrimary())
                .findFirst();
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

}

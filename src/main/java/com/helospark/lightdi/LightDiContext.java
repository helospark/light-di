package com.helospark.lightdi;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.properties.ValueResolver;

public class LightDiContext {
    private Map<DependencyDescriptor, Object> initializedSingletonBeans;
    private BeanFactory beanFactory;
    private ValueResolver valueResolver;
    private List<DependencyDescriptor> dependencyDescriptors;

    public LightDiContext(List<DependencyDescriptor> dependencyDescriptors, ValueResolver valueResolver,
            BeanFactory beanFactory) {
        initializedSingletonBeans = new HashMap<>();
        this.dependencyDescriptors = dependencyDescriptors;
        this.beanFactory = beanFactory;
        this.valueResolver = valueResolver;
    }

    public <T> T getOrCreateBean(Class<T> clazz) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(clazz)
                .build();
        return (T) getOrCreateDependencyInternal(query);
    }

    public ValueResolver getValueResolver() {
        return valueResolver;
    }

    public Object getOrCreateBean(DependencyDescriptorQuery query) {
        return getOrCreateDependencyInternal(query);
    }

    public void eagerInitAllBeans() {
        dependencyDescriptors.stream()
                .forEach(dependencyDescriptor -> getOrCreateBean(convertToQuery(dependencyDescriptor)));
    }

    private Object getOrCreateDependencyInternal(DependencyDescriptorQuery query) {
        Optional<DependencyDescriptor> initializedSingletonDescriptor = findInitializedSingletonDescriptor(query);
        if (initializedSingletonDescriptor.isPresent()) {
            return initializedSingletonBeans.get(initializedSingletonDescriptor.get());
        } else {
            List<DependencyDescriptor> dependencyToCreate = findDependencyDescriptor(dependencyDescriptors, query);
            if (dependencyToCreate.size() == 1) {
                DependencyDescriptor descriptorToUse = dependencyToCreate.get(0);
                Object createdBean = beanFactory.createBean(this, descriptorToUse);
                initializedSingletonBeans.put(descriptorToUse, createdBean);
                return createdBean;
            } else {
                throw new IllegalArgumentException("No single match " + dependencyToCreate);
            }
        }
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
                .filter(dependencyEntry -> isDependencyMatch(dependencyEntry, toFind))
                .collect(Collectors.toList());
    }

    private Optional<DependencyDescriptor> findPrimary(
            List<DependencyDescriptor> foundDependencies) {
        return foundDependencies.stream()
                .filter(dependency -> dependency.isPrimary())
                .findFirst();
    }

    private boolean isDependencyMatch(DependencyDescriptor dependencyDescriptor,
            DependencyDescriptorQuery toFind) {
        boolean classMatch = dependencyDescriptor.getClazz().equals(toFind.getClazz());
        boolean qualifierMatch = toFind.getQualifier()
                .map(toFindQualifier -> dependencyDescriptor.getQualifier().equals(toFindQualifier))
                .orElse(true);
        return classMatch && qualifierMatch;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getListOfBeans(Class<T> clazz) {
        List<Object> result = dependencyDescriptors.stream()
                .filter(descriptor -> clazz.isAssignableFrom(descriptor.getClazz()))
                .map(descriptor -> convertToQuery(descriptor))
                .map(descriptor -> this.getOrCreateBean(descriptor))
                .collect(Collectors.toList());
        return (List<T>) result;

    }

    private DependencyDescriptorQuery convertToQuery(DependencyDescriptor descriptor) {
        return DependencyDescriptorQuery.builder()
                .withClazz(descriptor.getClazz())
                .withQualifier(descriptor.getQualifier())
                .build();
    }
}

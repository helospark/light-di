package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.ReflectionUtil.createClassForName;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class BeanDefinitionCollector {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain;

    public BeanDefinitionCollector(List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain) {
        this.beanDefinitionCollectorChain = beanDefinitionCollectorChain;
    }

    public SortedSet<DependencyDescriptor> collectDependencyDescriptors(List<String> classes) {
        SortedSet<DependencyDescriptor> dependencyDescriptors = new TreeSet<>();
        for (String className : classes) {
            Class<?> clazz = createClassForName(className);
            beanDefinitionCollectorChain.stream()
                    .map(chain -> chain.collectDefinitions(clazz))
                    .forEach(descriptors -> dependencyDescriptors.addAll(descriptors));
        }
        return dependencyDescriptors;
    }
}

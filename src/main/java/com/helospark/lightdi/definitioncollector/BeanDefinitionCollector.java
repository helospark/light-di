package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.ReflectionUtil.createClassForName;

import java.util.ArrayList;
import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class BeanDefinitionCollector {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain;

    public BeanDefinitionCollector(List<BeanDefinitionCollectorChainItem> beanDefinitionCollectorChain) {
        this.beanDefinitionCollectorChain = beanDefinitionCollectorChain;
    }

    public List<DependencyDescriptor> collectDependencyDescriptors(List<String> classes) {
        List<DependencyDescriptor> dependencyDescriptors = new ArrayList<>();
        for (String className : classes) {
            Class<?> clazz = createClassForName(className);
            beanDefinitionCollectorChain.stream()
                    .filter(chain -> chain.isSupported(clazz))
                    .findFirst()
                    .map(chain -> chain.collectDefinitions(clazz))
                    .ifPresent(descriptors -> dependencyDescriptors.addAll(descriptors));
        }
        return dependencyDescriptors;
    }
}

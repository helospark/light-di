package com.helospark.lightdi.dependencywire.chain;

import java.lang.reflect.Method;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.dependencywire.chain.support.MethodDependencyCollector;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;

public class BeanDependencyWireChainItem implements DependencyWireChain {
    private MethodDependencyCollector methodDependencyCollector;

    public BeanDependencyWireChainItem(MethodDependencyCollector methodDependencyCollector) {
        this.methodDependencyCollector = methodDependencyCollector;
    }

    @Override
    public void collectDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependency) {
        if (dependency instanceof BeanDependencyDescriptor) {
            BeanDependencyDescriptor beanDescriptor = (BeanDependencyDescriptor) dependency;

            Method beanMethod = beanDescriptor.getMethod();
            List<InjectionDescriptor> injectionDescriptions = methodDependencyCollector.getSetterDependencies(
                    beanMethod,
                    dependencyDescriptors,
                    dependency);

            beanDescriptor.setMethodDescriptor(MethodDescriptor.builder()
                    .withMethod(beanMethod)
                    .withInjectionDescriptor(injectionDescriptions)
                    .build());
        }
    }

}

package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;

import com.helospark.lightdi.dependencywire.chain.support.chain.InjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.InjectDescriptorBuilderRequest;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class DependencyDescriptorBuilder {
    private List<InjectDescriptorBuilderChainItem> chain;

    public DependencyDescriptorBuilder(List<InjectDescriptorBuilderChainItem> chain) {
        this.chain = chain;
    }

    public InjectionDescriptor build(Parameter parameter, List<DependencyDescriptor> dependencyDescriptors, boolean required) {
        InjectDescriptorBuilderRequest request = InjectDescriptorBuilderRequest.builder()
                .withDependencyDescriptors(dependencyDescriptors)
                .withRequired(required)
                .withType(parameter.getType())
                .withGenericType(parameter.getParameterizedType())
                .withParameter(parameter)
                .build();
        return findWithChain(request);
    }

    public InjectionDescriptor build(Field parameter, List<DependencyDescriptor> dependencyDescriptors, boolean required) {
        InjectDescriptorBuilderRequest request = InjectDescriptorBuilderRequest.builder()
                .withDependencyDescriptors(dependencyDescriptors)
                .withRequired(required)
                .withType(parameter.getType())
                .withGenericType(parameter.getGenericType())
                .withParameter(parameter)
                .build();
        return findWithChain(request);
    }

    private InjectionDescriptor findWithChain(InjectDescriptorBuilderRequest request) {
        return chain.stream()
                .filter(item -> item.doesSupport(request))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find inject descriptor builder chain item"))
                .build(request);
    }

}

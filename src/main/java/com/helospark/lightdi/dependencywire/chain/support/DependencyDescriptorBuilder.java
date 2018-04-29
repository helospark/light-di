package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedSet;

import com.helospark.lightdi.dependencywire.chain.support.chain.InjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.InjectDescriptorBuilderRequest;
import com.helospark.lightdi.dependencywire.chain.support.domain.CustomParameter;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class DependencyDescriptorBuilder {
    private List<InjectDescriptorBuilderChainItem> chain;

    public DependencyDescriptorBuilder(List<InjectDescriptorBuilderChainItem> chain) {
        this.chain = chain;
    }

    public InjectionDescriptor build(CustomParameter parameter, SortedSet<DependencyDescriptor> dependencyDescriptors, boolean required,
            InjectionDescriptor descriptorToCreate) {
        InjectDescriptorBuilderRequest request = InjectDescriptorBuilderRequest.builder()
                .withDependencyDescriptors(dependencyDescriptors)
                .withRequired(required)
                .withType(parameter.getParameterType())
                .withGenericType(parameter.getGenericType())
                .withParameter(parameter.getAnnotatedParameterTypes())
                .withInjectionDescriptorToCreate(descriptorToCreate)
                .build();
        return findWithChain(request);
    }

    public InjectionDescriptor build(Field parameter, SortedSet<DependencyDescriptor> dependencyDescriptors, boolean required,
            InjectionDescriptor descriptorToCreate) {
        InjectDescriptorBuilderRequest request = InjectDescriptorBuilderRequest.builder()
                .withDependencyDescriptors(dependencyDescriptors)
                .withRequired(required)
                .withType(parameter.getType())
                .withGenericType(parameter.getGenericType())
                .withParameter(parameter)
                .withInjectionDescriptorToCreate(descriptorToCreate)
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

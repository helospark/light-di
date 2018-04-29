package com.helospark.lightdi.dependencywire.chain.support.chain;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class InjectDescriptorBuilderRequest {
    private SortedSet<DependencyDescriptor> dependencyDescriptors;
    private AnnotatedElement parameter;
    private Class<?> type;
    private Type genericType;
    private boolean required;
    private InjectionDescriptor injectionDescriptorToCreate;

    @Generated("SparkTools")
    private InjectDescriptorBuilderRequest(Builder builder) {
        this.dependencyDescriptors = builder.dependencyDescriptors;
        this.parameter = builder.parameter;
        this.type = builder.type;
        this.genericType = builder.genericType;
        this.required = builder.required;
        this.injectionDescriptorToCreate = builder.injectionDescriptorToCreate;
    }

    public InjectionDescriptor getInjectionDescriptorToCreate() {
        return injectionDescriptorToCreate;
    }

    public SortedSet<DependencyDescriptor> getDependencyDescriptors() {
        return dependencyDescriptors;
    }

    public AnnotatedElement getParameter() {
        return parameter;
    }

    public Type getGenericType() {
        return genericType;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private SortedSet<DependencyDescriptor> dependencyDescriptors = new TreeSet<>();
        private AnnotatedElement parameter;
        private Class<?> type;
        private Type genericType;
        private boolean required;
        private InjectionDescriptor injectionDescriptorToCreate;

        private Builder() {
        }

        public Builder withDependencyDescriptors(SortedSet<DependencyDescriptor> dependencyDescriptors) {
            this.dependencyDescriptors = dependencyDescriptors;
            return this;
        }

        public Builder withParameter(AnnotatedElement parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder withType(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder withGenericType(Type genericType) {
            this.genericType = genericType;
            return this;
        }

        public Builder withRequired(boolean required) {
            this.required = required;
            return this;
        }

        public Builder withInjectionDescriptorToCreate(InjectionDescriptor injectionDescriptorToCreate) {
            this.injectionDescriptorToCreate = injectionDescriptorToCreate;
            return this;
        }

        public InjectDescriptorBuilderRequest build() {
            return new InjectDescriptorBuilderRequest(this);
        }
    }

}

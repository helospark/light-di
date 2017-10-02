package com.helospark.lightdi.specialinject;

import java.util.Optional;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;

public class InjectionPoint {
    private DependencyDescriptor dependencyDescriptor;
    private InjectionDescriptor injectionDescriptor;
    private Optional<ConstructorDescriptor> constructorDescriptor;
    private Optional<MethodDescriptor> methodDescriptor;
    private Optional<FieldDescriptor> fieldDescriptor;

    @Generated("SparkTools")
    private InjectionPoint(Builder builder) {
        this.dependencyDescriptor = builder.dependencyDescriptor;
        this.injectionDescriptor = builder.injectionDescriptor;
        this.constructorDescriptor = builder.constructorDescriptor;
        this.methodDescriptor = builder.methodDescriptor;
        this.fieldDescriptor = builder.fieldDescriptor;
    }

    public DependencyDescriptor getDependencyDescriptor() {
        return dependencyDescriptor;
    }

    public InjectionDescriptor getInjectionDescriptor() {
        return injectionDescriptor;
    }

    public Optional<ConstructorDescriptor> getConstructorDescriptor() {
        return constructorDescriptor;
    }

    public Optional<MethodDescriptor> getMethodDescriptor() {
        return methodDescriptor;
    }

    public Optional<FieldDescriptor> getFieldDescriptor() {
        return fieldDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private DependencyDescriptor dependencyDescriptor;
        private InjectionDescriptor injectionDescriptor;
        private Optional<ConstructorDescriptor> constructorDescriptor = Optional.empty();
        private Optional<MethodDescriptor> methodDescriptor = Optional.empty();
        private Optional<FieldDescriptor> fieldDescriptor = Optional.empty();

        private Builder() {
        }

        public Builder withDependencyDescriptor(DependencyDescriptor dependencyDescriptor) {
            this.dependencyDescriptor = dependencyDescriptor;
            return this;
        }

        public Builder withInjectionDescriptor(InjectionDescriptor injectionDescriptor) {
            this.injectionDescriptor = injectionDescriptor;
            return this;
        }

        public Builder withConstructorDescriptor(Optional<ConstructorDescriptor> constructorDescriptor) {
            this.constructorDescriptor = constructorDescriptor;
            return this;
        }

        public Builder withMethodDescriptor(Optional<MethodDescriptor> methodDescriptor) {
            this.methodDescriptor = methodDescriptor;
            return this;
        }

        public Builder withFieldDescriptor(Optional<FieldDescriptor> fieldDescriptor) {
            this.fieldDescriptor = fieldDescriptor;
            return this;
        }

        public InjectionPoint build() {
            return new InjectionPoint(this);
        }
    }

}

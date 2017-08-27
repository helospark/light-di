package com.helospark.lightdi.descriptor.stereotype.constructor;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class ConstructorDescriptor {
    protected int index;
    private InjectionDescriptor dependencyDescriptor;

    @Generated("SparkTools")
    private ConstructorDescriptor(Builder builder) {
        this.index = builder.index;
        this.dependencyDescriptor = builder.dependencyDescriptor;
    }

    public ConstructorDescriptor(int index, InjectionDescriptor dependencyDescriptor) {
        this.index = index;
        this.dependencyDescriptor = dependencyDescriptor;
    }

    public int getIndex() {
        return index;
    }

    public InjectionDescriptor getDependencyDescriptor() {
        return dependencyDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private int index;
        private InjectionDescriptor dependencyDescriptor;

        private Builder() {
        }

        public Builder withIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder withDependencyDescriptor(InjectionDescriptor dependencyDescriptor) {
            this.dependencyDescriptor = dependencyDescriptor;
            return this;
        }

        public ConstructorDescriptor build() {
            return new ConstructorDescriptor(this);
        }
    }

}

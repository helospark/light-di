package com.helospark.lightdi.descriptor.stereotype.constructor;

import java.lang.reflect.Constructor;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class ConstructorDescriptor {
    private Constructor<?> constructor;
    protected int index;
    private InjectionDescriptor dependencyDescriptor;

    @Generated("SparkTools")
    private ConstructorDescriptor(Builder builder) {
        this.constructor = builder.constructor;
        this.index = builder.index;
        this.dependencyDescriptor = builder.dependencyDescriptor;
    }

    public int getIndex() {
        return index;
    }

    public InjectionDescriptor getDependencyDescriptor() {
        return dependencyDescriptor;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Constructor<?> constructor;
        private int index;
        private InjectionDescriptor dependencyDescriptor;

        private Builder() {
        }

        public Builder withConstructor(Constructor<?> constructor) {
            this.constructor = constructor;
            return this;
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

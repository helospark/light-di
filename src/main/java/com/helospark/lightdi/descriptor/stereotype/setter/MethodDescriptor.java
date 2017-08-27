package com.helospark.lightdi.descriptor.stereotype.setter;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class MethodDescriptor {
    private Method method;
    private List<InjectionDescriptor> injectionDescriptor;

    @Generated("SparkTools")
    private MethodDescriptor(Builder builder) {
        this.method = builder.method;
        this.injectionDescriptor = builder.injectionDescriptor;
    }

    public Method getMethod() {
        return method;
    }

    public List<InjectionDescriptor> getInjectionDescriptor() {
        return injectionDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Method method;
        private List<InjectionDescriptor> injectionDescriptor;

        private Builder() {
        }

        public Builder withMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder withInjectionDescriptor(List<InjectionDescriptor> injectionDescriptor) {
            this.injectionDescriptor = injectionDescriptor;
            return this;
        }

        public MethodDescriptor build() {
            return new MethodDescriptor(this);
        }
    }

}

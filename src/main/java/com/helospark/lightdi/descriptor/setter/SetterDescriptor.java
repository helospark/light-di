package com.helospark.lightdi.descriptor.setter;

import java.lang.reflect.Method;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class SetterDescriptor {
    private Method method;
    private InjectionDescriptor injectionDescriptor;

    @Generated("SparkTools")
    private SetterDescriptor(Builder builder) {
        this.method = builder.method;
        this.injectionDescriptor = builder.injectionDescriptor;
    }

    public Method getMethod() {
        return method;
    }

    public InjectionDescriptor getInjectionDescriptor() {
        return injectionDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Method method;
        private InjectionDescriptor injectionDescriptor;

        private Builder() {
        }

        public Builder withMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder withInjectionDescriptor(InjectionDescriptor injectionDescriptor) {
            this.injectionDescriptor = injectionDescriptor;
            return this;
        }

        public SetterDescriptor build() {
            return new SetterDescriptor(this);
        }
    }

}

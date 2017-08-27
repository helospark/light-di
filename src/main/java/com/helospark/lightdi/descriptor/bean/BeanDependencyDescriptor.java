package com.helospark.lightdi.descriptor.bean;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;

public class BeanDependencyDescriptor extends DependencyDescriptor {
    private DependencyDescriptor configurationDescriptor;
    private Method method;
    private MethodDescriptor methodDescriptor;

    @Generated("SparkTools")
    private BeanDependencyDescriptor(Builder builder) {
        this.clazz = builder.clazz;
        this.qualifier = builder.qualifier;
        this.isPrimary = builder.isPrimary;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
        this.configurationDescriptor = builder.configurationDescriptor;
        this.method = builder.method;
        this.methodDescriptor = builder.methodDescriptor;
    }

    public DependencyDescriptor getConfigurationDescriptor() {
        return configurationDescriptor;
    }

    public void setConfigurationDescriptor(DependencyDescriptor configurationDescriptor) {
        this.configurationDescriptor = configurationDescriptor;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public MethodDescriptor getMethodDescriptor() {
        return methodDescriptor;
    }

    public void setMethodDescriptor(MethodDescriptor methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;
        private boolean isPrimary;
        private List<Method> postConstructMethods;
        private List<Method> preDestroyMethods;
        private DependencyDescriptor configurationDescriptor;
        private Method method;
        private MethodDescriptor methodDescriptor;

        private Builder() {
        }

        public Builder withClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder withQualifier(String qualifier) {
            this.qualifier = qualifier;
            return this;
        }

        public Builder withIsPrimary(boolean isPrimary) {
            this.isPrimary = isPrimary;
            return this;
        }

        public Builder withPostConstructMethods(List<Method> postConstructMethods) {
            this.postConstructMethods = postConstructMethods;
            return this;
        }

        public Builder withPreDestroyMethods(List<Method> preDestroyMethods) {
            this.preDestroyMethods = preDestroyMethods;
            return this;
        }

        public Builder withConfigurationDescriptor(DependencyDescriptor configurationDescriptor) {
            this.configurationDescriptor = configurationDescriptor;
            return this;
        }

        public Builder withMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder withMethodDescriptor(MethodDescriptor methodDescriptor) {
            this.methodDescriptor = methodDescriptor;
            return this;
        }

        public BeanDependencyDescriptor build() {
            return new BeanDependencyDescriptor(this);
        }
    }

}

package com.helospark.lightdi.descriptor.bean;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
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
        this.scope = builder.scope;
        this.isLazy = builder.isLazy;
        this.isPrimary = builder.isPrimary;
        this.order = builder.order;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
        this.conditions = builder.conditions;
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

    @Override
    public String toString() {
        return "BeanDependencyDescriptor [configurationDescriptor=" + configurationDescriptor + ", method=" + method
                + ", methodDescriptor=" + methodDescriptor + ", toString()=" + super.toString() + "]";
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;
        private String scope;
        private boolean isLazy;
        private boolean isPrimary;
        private int order;
        private List<Method> postConstructMethods = Collections.emptyList();
        private List<Method> preDestroyMethods = Collections.emptyList();
        private List<DependencyCondition> conditions = Collections.emptyList();
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

        public Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder withIsLazy(boolean isLazy) {
            this.isLazy = isLazy;
            return this;
        }

        public Builder withIsPrimary(boolean isPrimary) {
            this.isPrimary = isPrimary;
            return this;
        }

        public Builder withOrder(int order) {
            this.order = order;
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

        public Builder withConditions(List<DependencyCondition> conditions) {
            this.conditions = conditions;
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

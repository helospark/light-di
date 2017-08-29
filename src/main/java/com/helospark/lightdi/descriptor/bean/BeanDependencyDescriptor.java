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
        this.scope = builder.scope;
        this.isLazy = builder.isLazy;
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

    @Override
    public String toString() {
        return "BeanDependencyDescriptor [configurationDescriptor=" + configurationDescriptor + ", method=" + method
                + ", methodDescriptor=" + methodDescriptor + ", toString()=" + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((configurationDescriptor == null) ? 0 : configurationDescriptor.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((methodDescriptor == null) ? 0 : methodDescriptor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BeanDependencyDescriptor other = (BeanDependencyDescriptor) obj;
        if (configurationDescriptor == null) {
            if (other.configurationDescriptor != null)
                return false;
        } else if (!configurationDescriptor.equals(other.configurationDescriptor))
            return false;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        if (methodDescriptor == null) {
            if (other.methodDescriptor != null)
                return false;
        } else if (!methodDescriptor.equals(other.methodDescriptor))
            return false;
        return true;
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

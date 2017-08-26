package com.helospark.lightdi.descriptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.constructor.ConstructorDescriptor;
import com.helospark.lightdi.descriptor.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.setter.SetterDescriptor;

public class DependencyDescriptor implements InjectionDescriptor {
    private Class<?> clazz;
    private String qualifier;
    private boolean isPrimary;
    private List<ConstructorDescriptor> constructorDescriptor;
    private List<SetterDescriptor> setterDescriptor;
    private List<FieldDescriptor> fieldDescriptor;
    private List<Method> postConstructMethods;
    private List<Method> preDestroyMethods;

    @Generated("SparkTools")
    private DependencyDescriptor(Builder builder) {
        this.clazz = builder.clazz;
        this.qualifier = builder.qualifier;
        this.isPrimary = builder.isPrimary;
        this.constructorDescriptor = builder.constructorDescriptor;
        this.setterDescriptor = builder.setterDescriptor;
        this.fieldDescriptor = builder.fieldDescriptor;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setPostConstructMethods(List<Method> postConstructMethods) {
        this.postConstructMethods = postConstructMethods;
    }

    public void setPreDestroyMethods(List<Method> preDestroyMethods) {
        this.preDestroyMethods = preDestroyMethods;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public List<ConstructorDescriptor> getConstructorDescriptors() {
        return constructorDescriptor;
    }

    public void setConstructorDescriptor(List<ConstructorDescriptor> constructorDescriptor) {
        this.constructorDescriptor = constructorDescriptor;
    }

    public List<SetterDescriptor> getSetterDescriptor() {
        return setterDescriptor;
    }

    public void setSetterDescriptor(List<SetterDescriptor> setterDescriptor) {
        this.setterDescriptor = setterDescriptor;
    }

    public List<FieldDescriptor> getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(List<FieldDescriptor> fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public List<ConstructorDescriptor> getConstructorDescriptor() {
        return constructorDescriptor;
    }

    public List<Method> getPostConstructMethods() {
        return postConstructMethods;
    }

    public List<Method> getPreDestroyMethods() {
        return preDestroyMethods;
    }

    @Override
    public String toString() {
        return "DependencyDescriptor [clazz=" + clazz + ", qualifier=" + qualifier + ", isPrimary=" + isPrimary
                + ", postConstructMethods=" + postConstructMethods
                + ", preDestroyMethods=" + preDestroyMethods + "]";
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
        private List<ConstructorDescriptor> constructorDescriptor = Collections.emptyList();
        private List<SetterDescriptor> setterDescriptor = Collections.emptyList();
        private List<FieldDescriptor> fieldDescriptor = Collections.emptyList();
        private List<Method> postConstructMethods = Collections.emptyList();
        private List<Method> preDestroyMethods = Collections.emptyList();

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

        public Builder withConstructorDescriptor(List<ConstructorDescriptor> constructorDescriptor) {
            this.constructorDescriptor = constructorDescriptor;
            return this;
        }

        public Builder withSetterDescriptor(List<SetterDescriptor> setterDescriptor) {
            this.setterDescriptor = setterDescriptor;
            return this;
        }

        public Builder withFieldDescriptor(List<FieldDescriptor> fieldDescriptor) {
            this.fieldDescriptor = fieldDescriptor;
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

        public DependencyDescriptor build() {
            return new DependencyDescriptor(this);
        }
    }

}

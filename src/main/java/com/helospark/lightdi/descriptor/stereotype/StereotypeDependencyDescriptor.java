package com.helospark.lightdi.descriptor.stereotype;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;

/**
 * Descriptor for classes annotated with stereotype annotations.
 * @author helospark
 */
public class StereotypeDependencyDescriptor extends DependencyDescriptor {
    private List<ConstructorDescriptor> constructorDescriptor;
    private List<MethodDescriptor> setterDescriptor;
    private List<FieldDescriptor> fieldDescriptor;

    @Generated("SparkTools")
    private StereotypeDependencyDescriptor(Builder builder) {
        this.clazz = builder.clazz;
        this.qualifier = builder.qualifier;
        this.isPrimary = builder.isPrimary;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
        this.constructorDescriptor = builder.constructorDescriptor;
        this.setterDescriptor = builder.setterDescriptor;
        this.fieldDescriptor = builder.fieldDescriptor;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    public List<ConstructorDescriptor> getConstructorDescriptor() {
        return constructorDescriptor;
    }

    public void setConstructorDescriptor(List<ConstructorDescriptor> constructorDescriptor) {
        this.constructorDescriptor = constructorDescriptor;
    }

    public List<MethodDescriptor> getSetterDescriptor() {
        return setterDescriptor;
    }

    public void setSetterDescriptor(List<MethodDescriptor> setterDescriptor) {
        this.setterDescriptor = setterDescriptor;
    }

    public List<FieldDescriptor> getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(List<FieldDescriptor> fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;
        private boolean isPrimary;
        private List<Method> postConstructMethods;
        private List<Method> preDestroyMethods;
        private List<ConstructorDescriptor> constructorDescriptor;
        private List<MethodDescriptor> setterDescriptor;
        private List<FieldDescriptor> fieldDescriptor;

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

        public Builder withConstructorDescriptor(List<ConstructorDescriptor> constructorDescriptor) {
            this.constructorDescriptor = constructorDescriptor;
            return this;
        }

        public Builder withSetterDescriptor(List<MethodDescriptor> setterDescriptor) {
            this.setterDescriptor = setterDescriptor;
            return this;
        }

        public Builder withFieldDescriptor(List<FieldDescriptor> fieldDescriptor) {
            this.fieldDescriptor = fieldDescriptor;
            return this;
        }

        public StereotypeDependencyDescriptor build() {
            return new StereotypeDependencyDescriptor(this);
        }
    }

}

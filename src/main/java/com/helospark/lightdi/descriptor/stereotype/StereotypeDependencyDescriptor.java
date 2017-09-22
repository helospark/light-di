package com.helospark.lightdi.descriptor.stereotype;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
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
        this.scope = builder.scope;
        this.isLazy = builder.isLazy;
        this.isPrimary = builder.isPrimary;
        this.order = builder.order;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
        this.conditions = builder.conditions;
        this.constructorDescriptor = builder.constructorDescriptor;
        this.setterDescriptor = builder.setterDescriptor;
        this.fieldDescriptor = builder.fieldDescriptor;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((constructorDescriptor == null) ? 0 : constructorDescriptor.hashCode());
        result = prime * result + ((fieldDescriptor == null) ? 0 : fieldDescriptor.hashCode());
        result = prime * result + ((setterDescriptor == null) ? 0 : setterDescriptor.hashCode());
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
        StereotypeDependencyDescriptor other = (StereotypeDependencyDescriptor) obj;
        if (constructorDescriptor == null) {
            if (other.constructorDescriptor != null)
                return false;
        } else if (!constructorDescriptor.equals(other.constructorDescriptor))
            return false;
        if (fieldDescriptor == null) {
            if (other.fieldDescriptor != null)
                return false;
        } else if (!fieldDescriptor.equals(other.fieldDescriptor))
            return false;
        if (setterDescriptor == null) {
            if (other.setterDescriptor != null)
                return false;
        } else if (!setterDescriptor.equals(other.setterDescriptor))
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
        private int order;
        private List<Method> postConstructMethods;
        private List<Method> preDestroyMethods;
        private List<DependencyCondition> conditions;
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

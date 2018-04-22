package com.helospark.lightdi.descriptor.stereotype.field;

import java.lang.reflect.Field;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class FieldDescriptor {
    private InjectionDescriptor injectionDescriptor;
    private Field field;

    @Generated("SparkTools")
    private FieldDescriptor(Builder builder) {
        this.injectionDescriptor = builder.injectionDescriptor;
        this.field = builder.field;
    }

    public Field getField() {
        return field;
    }

    public InjectionDescriptor getInjectionDescriptor() {
        return injectionDescriptor;
    }

    @Override
    public String toString() {
        return "FieldDescriptor [injectionDescriptor=" + injectionDescriptor + ", field=" + field + "]";
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private InjectionDescriptor injectionDescriptor;
        private Field field;

        private Builder() {
        }

        public Builder withInjectionDescriptor(InjectionDescriptor injectionDescriptor) {
            this.injectionDescriptor = injectionDescriptor;
            return this;
        }

        public Builder withField(Field field) {
            this.field = field;
            return this;
        }

        public FieldDescriptor build() {
            return new FieldDescriptor(this);
        }
    }

}

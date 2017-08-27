package com.helospark.lightdi.descriptor.stereotype.field;

import java.lang.reflect.Field;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class FieldDescriptor {
    protected String fieldName;
    private InjectionDescriptor injectionDescriptor;
    private Field field;

    @Generated("SparkTools")
    private FieldDescriptor(Builder builder) {
        this.fieldName = builder.fieldName;
        this.injectionDescriptor = builder.injectionDescriptor;
        this.field = builder.field;
    }

    public Field getField() {
        return field;
    }

    public FieldDescriptor(String fieldName, InjectionDescriptor dependencyDescriptor) {
        this.fieldName = fieldName;
        this.injectionDescriptor = dependencyDescriptor;
    }

    public String getFieldName() {
        return fieldName;
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
        private String fieldName;
        private InjectionDescriptor injectionDescriptor;
        private Field field;

        private Builder() {
        }

        public Builder withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
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

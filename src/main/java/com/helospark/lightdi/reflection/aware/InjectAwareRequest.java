package com.helospark.lightdi.reflection.aware;

import javax.annotation.Generated;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class InjectAwareRequest {
    private LightDiContext context;
    private DependencyDescriptor dependencyToCreate;
    private Object createdBean;

    @Generated("SparkTools")
    private InjectAwareRequest(Builder builder) {
        this.context = builder.context;
        this.dependencyToCreate = builder.dependencyToCreate;
        this.createdBean = builder.createdBean;
    }

    public LightDiContext getContext() {
        return context;
    }

    public DependencyDescriptor getDependencyToCreate() {
        return dependencyToCreate;
    }

    public Object getCreatedBean() {
        return createdBean;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private LightDiContext context;
        private DependencyDescriptor dependencyToCreate;
        private Object createdBean;

        private Builder() {
        }

        public Builder withContext(LightDiContext context) {
            this.context = context;
            return this;
        }

        public Builder withDependencyToCreate(DependencyDescriptor dependencyToCreate) {
            this.dependencyToCreate = dependencyToCreate;
            return this;
        }

        public Builder withCreatedBean(Object createdBean) {
            this.createdBean = createdBean;
            return this;
        }

        public InjectAwareRequest build() {
            return new InjectAwareRequest(this);
        }
    }

}

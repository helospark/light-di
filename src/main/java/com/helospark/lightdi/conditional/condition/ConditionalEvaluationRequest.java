package com.helospark.lightdi.conditional.condition;

import java.util.Collections;
import java.util.SortedSet;

import javax.annotation.Generated;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ConditionalEvaluationRequest {
    DependencyDescriptor descriptor;
    LightDiContext context;
    SortedSet<DependencyDescriptor> dependencies;

    public DependencyDescriptor getDescriptor() {
        return descriptor;
    }

    public LightDiContext getContext() {
        return context;
    }

    public SortedSet<DependencyDescriptor> getDependencies() {
        return dependencies;
    }

    @Generated("SparkTools")
    private ConditionalEvaluationRequest(Builder builder) {
        this.descriptor = builder.descriptor;
        this.context = builder.context;
        this.dependencies = builder.dependencies;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private DependencyDescriptor descriptor;
        private LightDiContext context;
        private SortedSet<DependencyDescriptor> dependencies = Collections.emptySortedSet();

        private Builder() {
        }

        public Builder withDescriptor(DependencyDescriptor descriptor) {
            this.descriptor = descriptor;
            return this;
        }

        public Builder withContext(LightDiContext context) {
            this.context = context;
            return this;
        }

        public Builder withDependencies(SortedSet<DependencyDescriptor> dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public ConditionalEvaluationRequest build() {
            return new ConditionalEvaluationRequest(this);
        }
    }

}

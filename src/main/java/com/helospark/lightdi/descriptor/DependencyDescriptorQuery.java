package com.helospark.lightdi.descriptor;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import javax.annotation.Generated;

/**
 * Required parameters to find a dependency.
 * @author helospark
 */
public class DependencyDescriptorQuery {
    private Optional<Class<?>> clazz;
    private Optional<String> qualifier;
    private boolean required;

    @Generated("SparkTools")
    private DependencyDescriptorQuery(Builder builder) {
        this.clazz = ofNullable(builder.clazz);
        this.qualifier = ofNullable(builder.qualifier);
        this.required = builder.required;
    }

    public Optional<Class<?>> getClazz() {
        return clazz;
    }

    public Optional<String> getQualifier() {
        return qualifier;
    }

    public boolean isRequired() {
        return required;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "DependencyDescriptorQuery [clazz=" + clazz + ", qualifier=" + qualifier + "]";
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;
        private boolean required = true;

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

        public Builder withRequired(boolean required) {
            this.required = required;
            return this;
        }

        public DependencyDescriptorQuery build() {
            return new DependencyDescriptorQuery(this);
        }
    }

}

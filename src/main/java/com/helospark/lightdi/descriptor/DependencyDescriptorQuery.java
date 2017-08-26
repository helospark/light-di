package com.helospark.lightdi.descriptor;

import java.util.Optional;

import javax.annotation.Generated;

public class DependencyDescriptorQuery {
    private Class<?> clazz;
    private Optional<String> qualifier;

    @Generated("SparkTools")
    private DependencyDescriptorQuery(Builder builder) {
        this.clazz = builder.clazz;
        this.qualifier = Optional.ofNullable(builder.qualifier);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Optional<String> getQualifier() {
        return qualifier;
    }

    /**
     * Creates builder to build {@link DependencyDescriptorQuery}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link DependencyDescriptorQuery}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;

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

        public DependencyDescriptorQuery build() {
            return new DependencyDescriptorQuery(this);
        }
    }

}

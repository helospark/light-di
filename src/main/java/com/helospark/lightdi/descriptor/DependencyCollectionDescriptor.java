package com.helospark.lightdi.descriptor;

import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

/**
 * Represents a list of dependencies to be injected.
 * <p>
 * Used when injecting an Collection by interface.
 *
 * @author helospark
 */
public class DependencyCollectionDescriptor implements InjectionDescriptor {
    private Class<? extends Collection<?>> collectionType;
    private List<DependencyDescriptor> dependencies;

    @Generated("SparkTools")
    private DependencyCollectionDescriptor(Builder builder) {
        this.collectionType = builder.collectionType;
        this.dependencies = builder.dependencies;
    }

    public Class<? extends Collection<?>> getCollectionType() {
        return collectionType;
    }

    public List<DependencyDescriptor> getDependencies() {
        return dependencies;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<? extends Collection<?>> collectionType;
        private List<DependencyDescriptor> dependencies;

        private Builder() {
        }

        public Builder withCollectionType(Class<? extends Collection<?>> collectionType) {
            this.collectionType = collectionType;
            return this;
        }

        public Builder withDependencies(List<DependencyDescriptor> dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public DependencyCollectionDescriptor build() {
            return new DependencyCollectionDescriptor(this);
        }
    }

}

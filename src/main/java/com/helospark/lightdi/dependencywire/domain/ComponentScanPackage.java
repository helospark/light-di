package com.helospark.lightdi.dependencywire.domain;

import java.util.Objects;

import javax.annotation.Generated;

public class ComponentScanPackage {
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ComponentScanPackage)) {
            return false;
        }
        ComponentScanPackage castOther = (ComponentScanPackage) other;
        return Objects.equals(packageName, castOther.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }

    @Generated("SparkTools")
    private ComponentScanPackage(Builder builder) {
        this.packageName = builder.packageName;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "ComponentScanPackage [packageName=" + packageName + "]";
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String packageName;

        private Builder() {
        }

        public Builder withPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public ComponentScanPackage build() {
            return new ComponentScanPackage(this);
        }
    }

}

package com.helospark.lightdi.dependencywire.domain;

import javax.annotation.Generated;

public class ComponentScanPackage {
    private String packageName;
    private Class<?> rootClass;
    private boolean onlyCurrentJar;

    @Generated("SparkTools")
    private ComponentScanPackage(Builder builder) {
        this.packageName = builder.packageName;
        this.rootClass = builder.rootClass;
        this.onlyCurrentJar = builder.onlyCurrentJar;
    }

    public String getPackageName() {
        return packageName;
    }

    public Class<?> getRootClass() {
        return rootClass;
    }

    public boolean isOnlyCurrentJar() {
        return onlyCurrentJar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (onlyCurrentJar ? 1231 : 1237);
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComponentScanPackage other = (ComponentScanPackage) obj;
        if (onlyCurrentJar != other.onlyCurrentJar)
            return false;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComponentScanPackage [packageName=" + packageName + ", rootClass=" + rootClass + ", onlyCurrentJar=" + onlyCurrentJar + "]";
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String packageName;
        private Class<?> rootClass;
        private boolean onlyCurrentJar;

        private Builder() {
        }

        public Builder withPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder withRootClass(Class<?> rootClass) {
            this.rootClass = rootClass;
            return this;
        }

        public Builder withOnlyCurrentJar(boolean onlyCurrentJar) {
            this.onlyCurrentJar = onlyCurrentJar;
            return this;
        }

        public ComponentScanPackage build() {
            return new ComponentScanPackage(this);
        }
    }

}

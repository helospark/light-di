package com.helospark.lightdi.descriptor;

import javax.annotation.Generated;

/**
 * Descriptor for "special values" that are not in the context.
 * Example InjectionPoint
 * @author helospark
 */
public class SpecialValueTypeInjectionDescriptor implements InjectionDescriptor {
    private Class<?> clazz;

    @Generated("SparkTools")
    private SpecialValueTypeInjectionDescriptor(Builder builder) {
        this.clazz = builder.clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;

        private Builder() {
        }

        public Builder withClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public SpecialValueTypeInjectionDescriptor build() {
            return new SpecialValueTypeInjectionDescriptor(this);
        }
    }

}

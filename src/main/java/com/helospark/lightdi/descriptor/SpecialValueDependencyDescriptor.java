package com.helospark.lightdi.descriptor;

import javax.annotation.Generated;

/**
 * Indicates that this is a special value to be injected.
 * @author helospark
 *
 */
public class SpecialValueDependencyDescriptor implements InjectionDescriptor {
    private Object specialValue;

    public Object getSpecialValue() {
        return specialValue;
    }

    @Generated("SparkTools")
    private SpecialValueDependencyDescriptor(Builder builder) {
        this.specialValue = builder.specialValue;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Object specialValue;

        private Builder() {
        }

        public Builder withSpecialValue(Object specialValue) {
            this.specialValue = specialValue;
            return this;
        }

        public SpecialValueDependencyDescriptor build() {
            return new SpecialValueDependencyDescriptor(this);
        }
    }

}

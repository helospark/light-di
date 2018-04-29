package com.helospark.lightdi;

import javax.annotation.Generated;

/**
 * Configuration for LightDi.
 * @author helospark
 */
public class LightDiContextConfiguration {
    private boolean checkForIntegrity;

    @Generated("SparkTools")
    private LightDiContextConfiguration(Builder builder) {
        this.checkForIntegrity = builder.checkForIntegrity;
    }

    public boolean isCheckForIntegrity() {
        return checkForIntegrity;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private boolean checkForIntegrity;

        private Builder() {
        }

        /**
         * Sets the integrity check of beans during startup.<br>
         * If not set, startup time will be faster, however errors may be hidden until a bean is first instantiated.<br>
         * It could be set based on environment, for example during development/CI check integrity, but on actual production device do not.
         * @param checkForIntegrity to set
         * @return Builder instance
         */
        public Builder withCheckForIntegrity(boolean checkForIntegrity) {
            this.checkForIntegrity = checkForIntegrity;
            return this;
        }

        public LightDiContextConfiguration build() {
            return new LightDiContextConfiguration(this);
        }
    }

}

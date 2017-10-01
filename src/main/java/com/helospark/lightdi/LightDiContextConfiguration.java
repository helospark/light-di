package com.helospark.lightdi;

import javax.annotation.Generated;

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

        public Builder withCheckForIntegrity(boolean checkForIntegrity) {
            this.checkForIntegrity = checkForIntegrity;
            return this;
        }

        public LightDiContextConfiguration build() {
            return new LightDiContextConfiguration(this);
        }
    }

}

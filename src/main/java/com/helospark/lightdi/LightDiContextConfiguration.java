package com.helospark.lightdi;

import javax.annotation.Generated;

/**
 * Configuration for LightDi.
 * 
 * @author helospark
 */
public class LightDiContextConfiguration {
    private boolean checkForIntegrity;
    private int threadNumber;

    @Generated("SparkTools")
    private LightDiContextConfiguration(Builder builder) {
	this.checkForIntegrity = builder.checkForIntegrity;
	this.threadNumber = builder.threadNumber;
    }

    public boolean isCheckForIntegrity() {
	return checkForIntegrity;
    }

    public int getThreadNumber() {
	return threadNumber;
    }

    @Generated("SparkTools")
    public static Builder builder() {
	return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
	private boolean checkForIntegrity;
	private int threadNumber;

	private Builder() {
	}

	public Builder withCheckForIntegrity(boolean checkForIntegrity) {
	    this.checkForIntegrity = checkForIntegrity;
	    return this;
	}

	public Builder withThreadNumber(int threadNumber) {
	    this.threadNumber = threadNumber;
	    return this;
	}

	public LightDiContextConfiguration build() {
	    return new LightDiContextConfiguration(this);
	}
    }

}

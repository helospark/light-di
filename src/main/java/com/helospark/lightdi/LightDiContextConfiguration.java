package com.helospark.lightdi;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import com.helospark.lightdi.internal.LightDiPlugin;

/**
 * Configuration for LightDi.
 * 
 * @author helospark
 */
public class LightDiContextConfiguration {
    private boolean checkForIntegrity;
    private int threadNumber;
    private boolean useComponentScanFile;
    private List<LightDiPlugin> plugins;

    @Generated("SparkTools")
    private LightDiContextConfiguration(Builder builder) {
        this.checkForIntegrity = builder.checkForIntegrity;
        this.threadNumber = builder.threadNumber;
        this.plugins = builder.plugins;
        this.useComponentScanFile = builder.useComponentScanFile;
    }

    public boolean isCheckForIntegrity() {
        return checkForIntegrity;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public List<LightDiPlugin> getPlugins() {
        return plugins;
    }

    public boolean isUseComponentScanFile() {
        return useComponentScanFile;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private boolean checkForIntegrity;
        private int threadNumber;
        private List<LightDiPlugin> plugins = Collections.emptyList();
        private boolean useComponentScanFile = false;

        private Builder() {
        }

        /**
         * Check for the integrity of all dependencies during start time (ex. all the dependencies are matched, etc.).<br>
         * It take some additional time to check for integrity during startup time, but avoids exception at runtime.
         * @param checkForIntegrity whether to check for integrity
         * @return builder
         */
        public Builder withCheckForIntegrity(boolean checkForIntegrity) {
            this.checkForIntegrity = checkForIntegrity;
            return this;
        }

        /**
         * Number of threads used within the framework.
         * For small context the overhead of creating and managing threads may actually hurt performance, but larger context could benefit from it. 
         * @param threadNumber number of threads, should be greater than or equal to 1
         * @return builder
         */
        public Builder withThreadNumber(int threadNumber) {
            this.threadNumber = threadNumber;
            return this;
        }

        /**
         * Plugins used to create the LightDi framework.
         * @param plugins list of plugins
         * @return builder
         */
        public Builder withAdditionalDependencies(List<LightDiPlugin> plugins) {
            this.plugins = plugins;
            return this;
        }

        /**
         * Use file for componentscan.
         * @param useComponentScanFile use file for componentscan
         * @return builder
         */
        public Builder withUseClasspathFile(boolean useComponentScanFile) {
            this.useComponentScanFile = useComponentScanFile;
            return this;
        }

        public LightDiContextConfiguration build() {
            return new LightDiContextConfiguration(this);
        }
    }

}

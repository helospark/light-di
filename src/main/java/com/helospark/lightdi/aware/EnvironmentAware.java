package com.helospark.lightdi.aware;

import com.helospark.lightdi.properties.Environment;

/**
 * Interface to implement if the object has to know about the Environment.
 * @author helospark
 */
public interface EnvironmentAware {

    /**
     * Sets the environment.
     * <p>
     * It is called after Autowire dependencies (constructors, setter and fields) are injected.<br>
     *
     * @param environment to set
     */
    public void setEnvironment(Environment environment);

}

package com.helospark.lightdi.aware;

import com.helospark.lightdi.LightDiContext;

/**
 * Interface to implement if the object has to know about it's LightDi context.
 * @author helospark
 *
 */
public interface ContextAware {

    /**
     * Set the context.
     * <p>
     * It is called after Autowire dependencies (constructors, setter and fields) are injected.<br>
     * @param context to set
     */
    public void setContext(LightDiContext context);

}

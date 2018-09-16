package com.helospark.lightdi.internal;

/**
 * Plugin in LightDI is the strongest means of modifying the internal behavior of the framework.
 * This is not something, you will usually implement, only use for cases where other means of configuration (ex. registering beans, creating BeanPostProcessor,
 * handling externally doesn't work).
 * Note that incorrect configuration within the plugin can cause errors within the LightDi framework. Since it's modifying the internal states, it may break with future updates.
 * Modifying the internal state of the framework gives lot of power to you, but lot of power comes with lot of responsibility, use wisely.
 * @author helospark
 */
public interface LightDiPlugin {

    /**
     * Customize internalDi framework.
     * For example you can put a proxy around the InteralDi and replace beans with a custom implementation.
     * @param original to customize, or return as is
     * @return customized InternalDi or the original
     */
    default InternalDi customizeInteralDi(InternalDi original) {
        return original;
    }

    /**
     * Called before the core initializes the internalDi framework.
     * @param internalDi you can configure this
     * Wrong configuration could cause issues within the LightDI framework
     */
    default void preconfigureInternalDi(InternalDi internalDi) {

    }

    /**
     * Called after the LightDi core initialized the internal di framework.
     * @param internalDi you can configure this
     * Wrong configuration could cause issues within the LightDI framework 
     */
    default void postconfigureInternalDi(InternalDi internalDi) {

    }

}

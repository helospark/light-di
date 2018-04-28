package com.helospark.lightdi;

import com.helospark.lightdi.exception.ContextInitializationFailedException;

/**
 * Entry point for LightDi.<p>
 * Note that you can use {@link LightDiContext} separately for more advanced usecases.
 * @author helospark
 */
public class LightDi {

    /**
     * Initialize a context by a stereotype annotated class.
     * @param clazz to initialize context with. Given class may contain ComponentScan, bean definitions, propertySources, etc.
     * @return created and initialized context
     * @throws ContextInitializationFailedException if the context initialization fails for any reason
     */
    public static LightDiContext initContextByClass(Class<?> clazz) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    /**
     * Initialize a context by a stereotype annotated class and configuration.
     * @param clazz to initialize context with. Given class may contain ComponentScan, bean definitions, propertySources, etc.
     * @param configuration configuration for the context
     * @return created and initialized context
     * @throws ContextInitializationFailedException if the context initialization fails for any reason
     */
    public static LightDiContext initContextByClass(Class<?> clazz, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    /**
     * Initialize a context by a package using classpath scan. It will collect annotated classes as beans in and under the given package.
     * @param packageName to scan
     * @return created and initialized context
     * @throws ContextInitializationFailedException if the context initialization fails for any reason
     */
    public static LightDiContext initContextByPackage(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependencyFromPackage(packageName);
        return lightDiContext;
    }

    /**
     * Initialize a context by a package using classpath scan. It will collect annotated classes as beans in and under the given package.
     * This variant include configuration
     * @param packageName to scan
     * @param configuration configuration for the context
     * @return created and initialized context
     * @throws ContextInitializationFailedException if the context initialization fails for any reason
     */
    public static LightDiContext initContextByPackage(String packageName, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependencyFromPackage(packageName);
        return lightDiContext;
    }

    /**
     * Abstract lightDi version.
     * @return version
     */
    public int version() {
        return 2;
    }

}
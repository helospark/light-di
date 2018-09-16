package com.helospark.lightdi.aware;

import java.util.Optional;

/**
 * Implementing class will be injected with the class that imported it.
 * <p>
 * It is useful for {@literal @}*Enable annotations, where the annotation contains additional configuration.
 * <p>
 * Following could be an example:
 * <pre>
 * public {@literal @}interface EnableAsync {
 *    String executorServiceBeanName;
 * }
 * </pre>
 * 
 * @author helospark
 */
public interface ImportingClassAware {

    /**
     * Sets the class that imported the current bean into the application context.
     * <p>
     * @param importingClass importing class or empty optional, if this class was not imported using {@literal @}Import (for example for component-scan, 
     * manual injection or {@literal @}Bean creation.<br>
     * Note that it does not apply transitively, only direct {@literal @}Import sets as not empty,
     * importing a class, that create the current bean as a {@literal @}Bean or {@literal @}ComponentScan does not.
     */
    public void setImportingClass(Optional<Class<?>> importingClass);

}

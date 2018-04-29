package com.helospark.lightdi.aware;

/**
 * Interface to implement if the object has to know about it's bean name.
 * @author helospark
 */
public interface BeanNameAware {

    /**
     * Set the bean name.
     * <p>
     * It is called after Autowire dependencies (constructors, setter and fields) are injected.
     * @param beanName name of the bean
     */
    public void setBeanName(String beanName);

}

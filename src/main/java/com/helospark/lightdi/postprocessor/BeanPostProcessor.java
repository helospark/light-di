package com.helospark.lightdi.postprocessor;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

/**
 * Hook that gets called after a bean is created.
 * <p>
 * It is called for every bean, but note that for Lazy init beans it will only be called when it's actually get created.<br>
 * BeanPostProcessors are automatically instantiated eagerly after the context started.<br>
 * Bean post processors never process bean post processors.
 * 
 * @author helospark
 */
public interface BeanPostProcessor {

    /**
     * Hook that gets called after a bean is created.
     * @param bean to post process
     * @param dependencyDescriptor descriptor of the bean
     * @return post processed bean instance. May be the same as the bean param.
     */
    Object postProcessAfterInitialization(Object bean, DependencyDescriptor dependencyDescriptor);

}

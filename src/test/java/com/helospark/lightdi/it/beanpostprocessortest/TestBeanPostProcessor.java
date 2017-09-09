package com.helospark.lightdi.it.beanpostprocessortest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.postprocessor.BeanPostProcessor;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, DependencyDescriptor dependencyDescriptor) {
        if (dependencyDescriptor.getClazz().equals(TestBean.class)) {
            ((TestBean) bean).setValue("setFromPostProcessor");
        }
        return bean;
    }

}

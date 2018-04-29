package com.helospark.lightdi.it.awaretest;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.aware.BeanNameAware;

@Configuration("customBeanName")
public class BeanNameAwareContext implements BeanNameAware {
    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

}

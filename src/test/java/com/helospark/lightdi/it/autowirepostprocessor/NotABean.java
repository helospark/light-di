package com.helospark.lightdi.it.autowirepostprocessor;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.aware.BeanNameAware;

public class NotABean implements BeanNameAware {
    @Autowired
    private BeanDependency beanDependency;
    @Value("${TEST_VALUE}")
    private String property;
    private String beanName;
    private SetterDependency setterDependency;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Autowired
    public void setSetterDependency(SetterDependency setterDependency) {
        this.setterDependency = setterDependency;
    }

    public BeanDependency getBeanDependency() {
        return beanDependency;
    }

    public String getProperty() {
        return property;
    }

    public String getBeanName() {
        return beanName;
    }

    public SetterDependency getSetterDependency() {
        return setterDependency;
    }

}

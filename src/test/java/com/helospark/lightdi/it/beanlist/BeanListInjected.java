package com.helospark.lightdi.it.beanlist;

import java.util.List;

import com.helospark.lightdi.annotation.Component;

@Component
public class BeanListInjected {
    public List<BeanClass> beans;

    public BeanListInjected(List<BeanClass> beans) {
        this.beans = beans;
    }

}

package com.helospark.lightdi.it.beanpostprocessortest;

import com.helospark.lightdi.annotation.Component;

@Component
public class TestBean {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

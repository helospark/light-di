package com.helospark.lightdi.it.testcontext1;

import javax.annotation.PostConstruct;

import com.helospark.lightdi.annotation.Component;

@Component
public class ComponentWithPostConstruct {
    private String fieldFilledInPostConstruct = null;

    @PostConstruct
    public void init() {
        fieldFilledInPostConstruct = "ok";
    }

    public String getFieldFilledInPostConstruct() {
        return fieldFilledInPostConstruct;
    }

}

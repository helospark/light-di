package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.PostConstruct;

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

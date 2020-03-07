package com.helospark.lightdi.it.autowiretoparent;

import com.helospark.lightdi.annotation.Autowired;

public class Parent {
    private Test test;

    public Test getTest() {
        return test;
    }

    @Autowired
    public void setTest(Test test) {
        this.test = test;
    }

}

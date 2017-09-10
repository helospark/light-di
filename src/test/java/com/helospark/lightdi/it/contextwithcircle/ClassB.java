package com.helospark.lightdi.it.contextwithcircle;

import com.helospark.lightdi.annotation.Component;

@Component
public class ClassB {
    private ClassA classA;

    public ClassB(ClassA classA) {
        this.classA = classA;
    }

}

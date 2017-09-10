package com.helospark.lightdi.it.contextwithcircle;

import com.helospark.lightdi.annotation.Component;

@Component
public class ClassA {
    private ClassB classB;

    public ClassA(ClassB classB) {
        this.classB = classB;
    }

}

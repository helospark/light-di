package com.helospark.lightdi.it.collectioninjecttest;

import java.util.List;

import com.helospark.lightdi.annotation.Component;

@Component
public class BeanToInjectCollection {
    private List<CommonInterface> commonInterface;

    public BeanToInjectCollection(List<CommonInterface> commonInterface) {
        this.commonInterface = commonInterface;
    }

    public List<CommonInterface> getCommonInterface() {
        return commonInterface;
    }

}

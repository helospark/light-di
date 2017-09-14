package com.helospark.lightdi.it.collectioninjecttest;

import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

@Component
public class BeanToInjectSet {
    private List<CommonInterface> commonInterface;

    @Autowired
    public void setCommonInterface(List<CommonInterface> commonInterface) {
        this.commonInterface = commonInterface;
    }

    public List<CommonInterface> getCommonInterface() {
        return commonInterface;
    }

}

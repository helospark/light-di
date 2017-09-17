package com.helospark.lightdi.it.collectioninjecttest;

import java.util.List;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

@Component
public class CollectionOnFieldBean {
    @Autowired
    private List<CommonInterface> commonInterface;

    public List<CommonInterface> getCommonInterface() {
        return commonInterface;
    }

}

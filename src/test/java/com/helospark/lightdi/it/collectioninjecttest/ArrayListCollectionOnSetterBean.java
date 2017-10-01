package com.helospark.lightdi.it.collectioninjecttest;

import java.util.ArrayList;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

@Component
public class ArrayListCollectionOnSetterBean {
    private ArrayList<CommonInterface> commonInterface;

    @Autowired
    public void setCommonInterface(ArrayList<CommonInterface> commonInterface) {
        this.commonInterface = commonInterface;
    }

    public ArrayList<CommonInterface> getCommonInterface() {
        return commonInterface;
    }

}

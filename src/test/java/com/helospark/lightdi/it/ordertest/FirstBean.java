package com.helospark.lightdi.it.ordertest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Order;

@Component
@Order(1)
public class FirstBean implements CommonOrderInterface {

    @Override
    public int getOrder() {
        return 1;
    }

}

package com.helospark.lightdi.it.ordertest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Order;

@Component
@Order(2)
public class SecondBean implements CommonOrderInterface {

    @Override
    public int getOrder() {
        return 2;
    }

}

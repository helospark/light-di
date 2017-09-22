package com.helospark.lightdi.it.ordertest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Order;

@Component
@Order(3)
public class ThirdBean implements CommonOrderInterface {

    @Override
    public int getOrder() {
        return 3;
    }

}

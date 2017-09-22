package com.helospark.lightdi.it.ordertest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Order;
import com.helospark.lightdi.annotation.Primary;

@Component("qualified")
@Primary
@Order(0)
public class FirstPrimaryBean {

}

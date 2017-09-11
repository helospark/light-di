package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnProperty;

@Component
@ConditionalOnProperty(property = "TEST_VALUE", havingValue = "asd")
public class ConditionalOnPropertyShouldCreateBean {

}

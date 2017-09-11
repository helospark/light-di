package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnProperty;

@Component
@ConditionalOnProperty(property = "notExistent", havingValue = "asd")
public class ConditionalOnPropertyShouldNotCreateBean {

}

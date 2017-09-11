package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnClass;

@Component
@ConditionalOnClass("com.helospark.lightdi.it.conditionaltest.TestConfiguration")
public class ConditionalOnClassShouldCreateBean {

}

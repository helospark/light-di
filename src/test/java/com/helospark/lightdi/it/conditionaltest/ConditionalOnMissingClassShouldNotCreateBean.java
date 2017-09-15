package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnMissingClass;

@Component
@ConditionalOnMissingClass("com.helospark.lightdi.it.conditionaltest.TestConfiguration")
public class ConditionalOnMissingClassShouldNotCreateBean {

}

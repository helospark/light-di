package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnBean;

@Component
@ConditionalOnBean(TestConfiguration.class)
public class ConditionalOnBeanShouldCreateBean {

}

package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnBean;

@Component
@ConditionalOnBean(ConditionalOnClassShouldNotCreateBean.class)
public class ConditionalOnBeanToConditionalOnClass {

}

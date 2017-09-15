package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.ConditionalOnMissingBean;

@Component
@ConditionalOnMissingBean(NotABean.class)
public class ConditionalOnMissingBeanShouldCreateBean {

}

package com.helospark.lightdi.it.customconditional.beans;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.customconditional.ConditionalOnBeanNameContaining;

@Component
@ConditionalOnBeanNameContaining(nameContains = "other")
public class NotMatchingNameBean {

}

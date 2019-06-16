package com.helospark.lightdi.it.customconditional.beans;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.customconditional.ConditionalOnCustomValue;

@Component
@ConditionalOnCustomValue
public class CustomValueConditionalBean {

}

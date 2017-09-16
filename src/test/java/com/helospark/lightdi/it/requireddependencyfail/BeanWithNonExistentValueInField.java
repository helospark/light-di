package com.helospark.lightdi.it.requireddependencyfail;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class BeanWithNonExistentValueInField {
    @Value("${NON_EXISTENT}")
    private String field;
}

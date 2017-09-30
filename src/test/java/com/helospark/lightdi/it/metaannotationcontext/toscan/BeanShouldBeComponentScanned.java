package com.helospark.lightdi.it.metaannotationcontext.toscan;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class BeanShouldBeComponentScanned {
    @Value("${TEST_VALUE}")
    private String value;

    public String getValue() {
        return value;
    }

}

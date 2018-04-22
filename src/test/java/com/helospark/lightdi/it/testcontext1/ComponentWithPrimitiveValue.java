package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithPrimitiveValue {
    @Value("${INT_VALUE}")
    private int intValue;

    public int getIntValue() {
        return intValue;
    }

}

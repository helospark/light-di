package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithPrimitiveValue {
    @Value("${INT_VALUE}")
    private int intValue;
    @Value("${INT_VALUE}")
    private long longValue;
    @Value("${BOOLEAN_VALUE}")
    private boolean boolValue;

    public int getIntValue() {
        return intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

}

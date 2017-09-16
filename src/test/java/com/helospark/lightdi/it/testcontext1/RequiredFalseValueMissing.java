package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class RequiredFalseValueMissing {
    @Value(value = "${NON_EXISTENT}", required = false)
    private String value;

    public String getValue() {
        return value;
    }

}

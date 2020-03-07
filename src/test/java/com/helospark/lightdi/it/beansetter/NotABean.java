package com.helospark.lightdi.it.beansetter;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;

public class NotABean {
    private TestBean testBeanSetInSetter;
    @Autowired
    private TestBean testBeanSetAsValue;
    @Value("${TEST_VALUE}")
    private String value;

    @Autowired
    public void setTestBeanSetInSetter(TestBean testBean) {
        this.testBeanSetInSetter = testBean;
    }

    public TestBean getTestBeanSetInSetter() {
        return testBeanSetInSetter;
    }

    public TestBean getTestBeanSetAsValue() {
        return testBeanSetAsValue;
    }

    public String getValue() {
        return value;
    }

}

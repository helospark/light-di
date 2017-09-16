package com.helospark.lightdi.it.testclassload;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
@ComponentScan("com.helospark.lightdi.it.othertestpackage")
public class ClassInitConfigurationClass {

}

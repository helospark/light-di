package com.helospark.lightdi.it.importingclassaware;

import com.helospark.lightdi.annotation.Configuration;

@Configuration
@EnableDummyService(configuration = "testConfiguration")
public class ImportingClassAwareConfiguration {

}

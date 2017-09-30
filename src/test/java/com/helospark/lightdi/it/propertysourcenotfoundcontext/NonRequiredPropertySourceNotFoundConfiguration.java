package com.helospark.lightdi.it.propertysourcenotfoundcontext;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/this_should_not_exists", ignoreResourceNotFound = true)
public class NonRequiredPropertySourceNotFoundConfiguration {

}

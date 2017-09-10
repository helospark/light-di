package com.helospark.lightdi.it.propertyoverride;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.properties.Environment;

@Configuration
@PropertySource("classpath:test_properties.properties")
public class PropertyOverrideConfiguration {

}

@Configuration
@PropertySource(value = "classpath:test_properties_override.properties", order = Environment.HIGHEST_PROPERTY_ORDER)
class OtherConfiguration {

}

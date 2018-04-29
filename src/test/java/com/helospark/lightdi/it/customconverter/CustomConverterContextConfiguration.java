package com.helospark.lightdi.it.customconverter;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:test_properties.properties")
public class CustomConverterContextConfiguration {

}

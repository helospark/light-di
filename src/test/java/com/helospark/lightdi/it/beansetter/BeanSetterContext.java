package com.helospark.lightdi.it.beansetter;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:test_properties.properties")
public class BeanSetterContext {

    @Bean
    public NotABean createBean() {
        return new NotABean();
    }

}

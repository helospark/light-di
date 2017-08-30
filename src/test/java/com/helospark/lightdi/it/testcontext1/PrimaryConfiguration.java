package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Primary;

@Configuration
public class PrimaryConfiguration {

    @Bean
    public PrimaryTestBean notPrimary() {
        return new PrimaryTestBean("notPrimary");
    }

    @Bean
    @Primary
    public PrimaryTestBean primary() {
        return new PrimaryTestBean("primary");
    }
}

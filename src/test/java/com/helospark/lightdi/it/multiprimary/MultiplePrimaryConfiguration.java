package com.helospark.lightdi.it.multiprimary;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Order;
import com.helospark.lightdi.annotation.Primary;

@Configuration
public class MultiplePrimaryConfiguration {

    @Bean
    public MultiplePrimaryBean notPrimary() {
        return new MultiplePrimaryBean("notPrimary");
    }

    @Bean
    @Primary
    @Order(-1)
    public MultiplePrimaryBean firstPrimary() {
        return new MultiplePrimaryBean("firstPrimary");
    }

    @Bean
    @Primary
    @Order(0)
    public MultiplePrimaryBean secondPrimary() {
        return new MultiplePrimaryBean("secondPrimary");
    }

    public static class MultiplePrimaryBean {
        private String name;

        public MultiplePrimaryBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}

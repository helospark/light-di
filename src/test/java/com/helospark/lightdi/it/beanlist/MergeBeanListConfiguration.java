package com.helospark.lightdi.it.beanlist;

import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
@ComponentScan
public class MergeBeanListConfiguration {

    @Bean
    public BeanClass bean() {
        return new BeanClass("a");
    }

    @Bean
    public List<BeanClass> beans() {
        return Arrays.asList(new BeanClass("b"), new BeanClass("c"));
    }

    @Bean
    public List<BeanClass> beans2() {
        return Arrays.asList(new BeanClass("d"), new BeanClass("e"));
    }
}

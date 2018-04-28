package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class CircularConditionalOnBeanDependency {

    @Bean
    @ConditionalOnBean(ConditionalOnBeanSecondBean.class)
    public ConditionalOnBeanFirstBean firstBean() {
        return new ConditionalOnBeanFirstBean();
    }

    @Bean
    @ConditionalOnBean(ConditionalOnBeanFirstBean.class)
    public ConditionalOnBeanSecondBean secondBean() {
        return new ConditionalOnBeanSecondBean();
    }

    public static class ConditionalOnBeanFirstBean {

    }

    public static class ConditionalOnBeanSecondBean {

    }

}

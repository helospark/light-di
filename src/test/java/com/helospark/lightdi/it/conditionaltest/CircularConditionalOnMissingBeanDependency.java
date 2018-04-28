package com.helospark.lightdi.it.conditionaltest;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.annotation.ConditionalOnMissingBean;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class CircularConditionalOnMissingBeanDependency {

    @Bean
    @ConditionalOnMissingBean(ConditionalOnMissingBeanSecondBean.class)
    public ConditionalOnMissingBeanFirstBean firstBean() {
        return new ConditionalOnMissingBeanFirstBean();
    }

    @Bean
    @ConditionalOnMissingBean(ConditionalOnMissingBeanFirstBean.class)
    public ConditionalOnMissingBeanSecondBean secondBean() {
        return new ConditionalOnMissingBeanSecondBean();
    }

    public static class ConditionalOnMissingBeanFirstBean {

    }

    public static class ConditionalOnMissingBeanSecondBean {

    }

}

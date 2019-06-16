package com.helospark.lightdi.it.customconditional;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Configuration
@ComponentScan
public class CustomConditionalTestConfiguration {

    @Bean
    @ConditionalOnBeanNameContaining(nameContains = "beanwithmatchingname")
    public NotABean notabeanwithmatchingname() {
        return new NotABean();
    }

    @Bean
    @ConditionalOnBeanNameContaining(nameContains = "other")
    public NotABean notabeanwithnamenotmatching() {
        return new NotABean();
    }

}

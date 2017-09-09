package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Import;
import com.helospark.lightdi.it.thirdpackage.ThirdPackageConfiguration;

@Configuration
@ComponentScan("com.helospark.lightdi.it.othertestpackage")
@Import(ThirdPackageConfiguration.class)
public class ConfigurationClass {

    @Bean
    public NonAnnotatedClass createNonAnnotatedClass() {
        return new NonAnnotatedClass();
    }

    @Bean
    public OtherNonAnnotatedClass createNonAnnotatedClassWithDependency(TestDependency dependency) {
        return new OtherNonAnnotatedClass(dependency);
    }

    @Bean("otherQualifiedBean")
    public QualifiedBean otherQualifiedBean() {
        return new QualifiedBean();
    }
}

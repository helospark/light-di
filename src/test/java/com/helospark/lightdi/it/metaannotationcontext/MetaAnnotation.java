package com.helospark.lightdi.it.metaannotationcontext;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Import;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.it.metaannotationcontext.toscan.BeanShouldBeComponentScanned;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Configuration
@Import(AnnotationImportedConfiguration.class)
@PropertySource("classpath:test_properties.properties")
@ComponentScan(basePackageClasses = BeanShouldBeComponentScanned.class)
public @interface MetaAnnotation {

}

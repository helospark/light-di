package com.helospark.lightdi.it.importingclassaware;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.helospark.lightdi.annotation.Import;

@Retention(RUNTIME)
@Target(TYPE)
@Import(DummyServiceConfiguration.class)
public @interface EnableDummyService {

    public String configuration();

}

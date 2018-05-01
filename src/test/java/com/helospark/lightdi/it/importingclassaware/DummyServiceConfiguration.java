package com.helospark.lightdi.it.importingclassaware;

import java.util.Optional;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Eager;
import com.helospark.lightdi.aware.ImportingClassAware;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.AnnotationUtil;

@Configuration
@Eager
public class DummyServiceConfiguration implements ImportingClassAware {
    private String configurationValue;

    public String getConfigurationValue() {
        return configurationValue;
    }

    @Override
    public void setImportingClass(Optional<Class<?>> importingClass) {
        this.configurationValue = importingClass
                .map(clazz -> AnnotationUtil.getSingleAnnotationOfType(clazz, EnableDummyService.class))
                .map(annotation -> annotation.getAttributeAs("configuration", String.class))
                .orElseThrow(() -> new IllegalConfigurationException("Do not directly create use EnableDummyService"));
    }

}

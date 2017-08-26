package com.helospark.lightdi.scanner;

import java.util.List;

import com.helospark.lightdi.properties.converter.StringPropertyConverter;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class LightDiClasspathScanner {
    private static final String DEFAULT_CONVERTER_BEANS = StringPropertyConverter.class.getPackage().getName();

    public List<String> scanClasspathForBeanClassNames(String packageName) {
        return new FastClasspathScanner(packageName, DEFAULT_CONVERTER_BEANS)
                .scan()
                .getNamesOfAllClasses();
    }
}

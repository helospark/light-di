package com.helospark.lightdi.scanner;

import java.util.List;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class LightDiClasspathScanner {

    public List<String> scanClasspathForBeanClassNames(String packageName) {
        return new FastClasspathScanner(packageName)
                .scan()
                .getNamesOfAllClasses();
    }
}

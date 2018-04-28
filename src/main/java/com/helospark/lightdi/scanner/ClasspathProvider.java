package com.helospark.lightdi.scanner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ClasspathProvider {

    public List<String> provideClasspathEntries() {
        String classpathSeparator = File.pathSeparator;
        String[] classpath = System.getProperty("java.class.path").split(classpathSeparator);
        return Arrays.asList(classpath);
    }

}

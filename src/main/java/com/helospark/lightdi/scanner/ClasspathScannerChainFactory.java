package com.helospark.lightdi.scanner;

import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.scanner.support.ClassPathToClassNameConverter;
import com.helospark.lightdi.scanner.support.ClassToSourceConverter;

public class ClasspathScannerChainFactory {

    public ClasspathScannerChain create() {
        ClassPathToClassNameConverter classPathToClassName = new ClassPathToClassNameConverter();
        ClassToSourceConverter classToSource = new ClassToSourceConverter();

        SimpleJarScanner simpleJarScanner = new SimpleJarScanner(classPathToClassName, classToSource);
        SimpleDirectoryScanner simpleDirectoryScanner = new SimpleDirectoryScanner(classPathToClassName, classToSource);
        FastClasspathScannerChainItem fastClasspathScannerChainItem = new FastClasspathScannerChainItem();

        List<ClasspathScannerChainItem> chain = Arrays.asList(simpleJarScanner, simpleDirectoryScanner, fastClasspathScannerChainItem);

        return new ClasspathScannerChain(chain);
    }
}

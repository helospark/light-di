package com.helospark.lightdi.scanner;

import java.util.Arrays;
import java.util.List;

public class ClasspathScannerChainFactory {

    public ClasspathScannerChain create() {
        PreprocessedFileLocationProvider locationProvider = new PreprocessedFileLocationProvider(new ClasspathProvider());
        PreprocessedAnnotationScannerChainItem preprocessedAnnotationScanner = new PreprocessedAnnotationScannerChainItem(locationProvider);
        FastClasspathScannerChainItem fastClasspathScannerChainItem = new FastClasspathScannerChainItem();

        List<ClasspathScannerChainItem> chain = Arrays.asList(preprocessedAnnotationScanner,
                fastClasspathScannerChainItem);

        return new ClasspathScannerChain(chain);
    }
}

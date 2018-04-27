package com.helospark.lightdi.scanner;

import java.util.Arrays;
import java.util.List;

public class ClasspathScannerChainFactory {

    public ClasspathScannerChain create() {
        PreprocessedAnnotationScannerChainItem preprocessedAnnotationScanner = new PreprocessedAnnotationScannerChainItem();
        FastClasspathScannerChainItem fastClasspathScannerChainItem = new FastClasspathScannerChainItem();

        List<ClasspathScannerChainItem> chain = Arrays.asList(preprocessedAnnotationScanner,
                fastClasspathScannerChainItem);

        return new ClasspathScannerChain(chain);
    }
}

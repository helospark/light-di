package com.helospark.lightdi.scanner;

import java.util.List;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

public class ClasspathScannerChain {
    private List<ClasspathScannerChainItem> chain;

    public ClasspathScannerChain(List<ClasspathScannerChainItem> chain) {
        this.chain = chain;
    }

    public List<String> scan(ComponentScanPackage componentScanPackage) {
        return chain.stream()
                .filter(chainItem -> chainItem.doesSupport(componentScanPackage))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot scan classpath " + componentScanPackage))
                .scan(componentScanPackage);
    }

}

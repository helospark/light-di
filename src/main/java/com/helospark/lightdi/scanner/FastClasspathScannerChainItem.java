package com.helospark.lightdi.scanner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class FastClasspathScannerChainItem implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastClasspathScannerChainItem.class);

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " with fastclasspathscanner");
        return new FastClasspathScanner(componentScanPackage.getPackageName())
                .scan()
                .getNamesOfAllClasses();
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        return isFastClassPathScannerOnClasspath(); // always supported
    }

    private boolean isFastClassPathScannerOnClasspath() {
        try {
            Class.forName("io.github.lukehutch.fastclasspathscanner.FastClasspathScanner");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}

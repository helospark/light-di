package com.helospark.lightdi.scanner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class FastClasspathScannerChainItem implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastClasspathScannerChainItem.class);

    private boolean isFastClasspathDependencyAlreadyChecked = false;

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " with fastclasspathscanner");
        assertFastClasspathScannerDependencyOnClasspath();
        return new FastClasspathScanner(componentScanPackage.getPackageName())
                .scan()
                .getNamesOfAllClasses();
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        return true; // always supported
    }

    private void assertFastClasspathScannerDependencyOnClasspath() {
        if (!isFastClasspathDependencyAlreadyChecked) {
            if (!isFastClassPathScannerOnClasspath()) {
                throw new IllegalStateException(
                        "Full classpath scan requested, but FastClasspathScanner is not added to classpath. Please add it as a dependency.");
            }
            isFastClasspathDependencyAlreadyChecked = true;
        }
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

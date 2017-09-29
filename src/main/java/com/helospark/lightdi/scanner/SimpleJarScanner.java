package com.helospark.lightdi.scanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.scanner.support.ClassPathToClassNameConverter;
import com.helospark.lightdi.scanner.support.ClassToSourceConverter;

public class SimpleJarScanner implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJarScanner.class);

    private ClassPathToClassNameConverter classPathToClassName;
    private ClassToSourceConverter classToSource;

    public SimpleJarScanner(ClassPathToClassNameConverter classPathToClassName, ClassToSourceConverter classToSource) {
        this.classPathToClassName = classPathToClassName;
        this.classToSource = classToSource;
    }

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " as jar");

        try {
            List<String> result = new ArrayList<>();
            try (JarFile zipFile = new JarFile(toFile(componentScanPackage))) {

                Enumeration<? extends JarEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = classPathToClassName.pathToClass(entry.getName());
                        if (className.startsWith(componentScanPackage.getPackageName())) {
                            result.add(className);
                        }
                    }
                }

                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        if (componentScanPackage.isOnlyCurrentJar()) {
            File file = toFile(componentScanPackage);
            if (!file.exists() || !file.getName().endsWith(".jar")) {
                LOGGER.debug("Skipping " + file + ", because it is not a jar");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private File toFile(ComponentScanPackage componentScanPackage) {
        File file = classToSource.geSourceLocationName(componentScanPackage.getRootClass());
        return file;
    }

}

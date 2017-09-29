package com.helospark.lightdi.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.scanner.support.ClassPathToClassNameConverter;
import com.helospark.lightdi.scanner.support.ClassToSourceConverter;

public class SimpleDirectoryScanner implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDirectoryScanner.class);
    private ClassPathToClassNameConverter classPathToClassName;
    private ClassToSourceConverter classToSource;

    public SimpleDirectoryScanner(ClassPathToClassNameConverter classPathToClassName, ClassToSourceConverter classToSource) {
        this.classPathToClassName = classPathToClassName;
        this.classToSource = classToSource;
    }

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " as directory");

        File rootDirectory = toFile(componentScanPackage);
        String pathFromRoot = "";
        List<String> result = listClassesInDirectory(rootDirectory, pathFromRoot)
                .stream()
                .filter(className -> className.startsWith(componentScanPackage.getPackageName()))
                .collect(Collectors.toList());
        return result;
    }

    private List<String> listClassesInDirectory(File directory, String pathFromRoot) {
        List<String> result = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(listClassesInDirectory(file, pathFromRoot + file.getName() + '/'));
            } else if (file.isFile() && file.getName().endsWith(".class")) {
                String fullPath = pathFromRoot + file.getName();
                String className = classPathToClassName.pathToClass(fullPath);
                result.add(className);
            }
        }
        return result;
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        if (componentScanPackage.isOnlyCurrentJar()) {
            File file = toFile(componentScanPackage);
            if (!file.exists() || !file.isDirectory()) {
                LOGGER.debug("Not directory, skipping " + file);
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

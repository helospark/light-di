package com.helospark.lightdi.dependencywire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.properties.converter.StringPropertyConverter;
import com.helospark.lightdi.scanner.LightDiClasspathScanner;

public class RecursiveDependencyDescriptorCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecursiveDependencyDescriptorCollector.class);

    private static final String DEFAULT_CONVERTER_PACKAGE = StringPropertyConverter.class.getPackage().getName();

    private LightDiClasspathScanner lightDiClasspathScanner;
    private BeanDefinitionCollector beanDefinitionCollector;
    private ComponentScanCollector componentScanCollector;

    public RecursiveDependencyDescriptorCollector(LightDiClasspathScanner lightDiClasspathScanner, BeanDefinitionCollector beanDefinitionCollector,
            ComponentScanCollector componentScanCollector) {
        this.lightDiClasspathScanner = lightDiClasspathScanner;
        this.beanDefinitionCollector = beanDefinitionCollector;
        this.componentScanCollector = componentScanCollector;
    }

    public SortedSet<DependencyDescriptor> collectDependencies(String packageName) {
        SortedSet<DependencyDescriptor> dependencyDescriptors = new TreeSet<>();
        List<String> alreadyScannedPackages = new ArrayList<>();
        List<String> packagesToScan = Arrays.asList(packageName, DEFAULT_CONVERTER_PACKAGE);

        packagesToScan.stream()
                .forEach(currentPackageName -> dependencyDescriptors
                        .addAll(collectDepenenciesRecursively(currentPackageName, alreadyScannedPackages)));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Following dependency descriptors are annotated for LightDI: " + dependencyDescriptors);
        }
        return dependencyDescriptors;
    }

    public SortedSet<DependencyDescriptor> collectDependenciesStartingFromClass(Class<?> clazz) {
        return collectClasses(new ArrayList<>(), Collections.singletonList(clazz.getName()));
    }

    private SortedSet<DependencyDescriptor> collectDepenenciesRecursively(String packageName, List<String> alreadyScannerPackages) {
        if (!alreadyScannerPackages.contains(packageName)) {
            alreadyScannerPackages.add(packageName);

            List<String> classes = lightDiClasspathScanner.scanClasspathForBeanClassNames(packageName);
            return collectClasses(alreadyScannerPackages, classes);
        } else {
            return new TreeSet<>();
        }
    }

    private SortedSet<DependencyDescriptor> collectClasses(List<String> alreadyScannerPackages, List<String> classes) {
        SortedSet<DependencyDescriptor> dependencyDescriptors = beanDefinitionCollector.collectDependencyDescriptors(classes);

        List<String> findOtherPackages = componentScanCollector.collectComponentScan(dependencyDescriptors);

        findOtherPackages.stream()
                .filter(scannedPackageName -> !alreadyScannerPackages.contains(scannedPackageName))
                .forEach(scannedPackageName -> dependencyDescriptors
                        .addAll(collectDepenenciesRecursively(scannedPackageName, alreadyScannerPackages)));

        return dependencyDescriptors;
    }
}

package com.helospark.lightdi.dependencywire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public List<DependencyDescriptor> collectDependencies(String packageName) {
        List<DependencyDescriptor> dependencyDescriptors = new ArrayList<>();
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

    private List<DependencyDescriptor> collectDepenenciesRecursively(String packageName, List<String> alreadyScannerPackages) {
        if (!alreadyScannerPackages.contains(packageName)) {
            alreadyScannerPackages.add(packageName);

            List<String> classes = lightDiClasspathScanner.scanClasspathForBeanClassNames(packageName);
            List<DependencyDescriptor> dependencyDescriptors = beanDefinitionCollector.collectDependencyDescriptors(classes);

            List<String> findOtherPackages = componentScanCollector.collectComponentScan(dependencyDescriptors);

            findOtherPackages.stream()
                    .filter(scannedPackageName -> !alreadyScannerPackages.contains(scannedPackageName))
                    .forEach(scannedPackageName -> dependencyDescriptors
                            .addAll(collectDepenenciesRecursively(scannedPackageName, alreadyScannerPackages)));

            return dependencyDescriptors;
        } else {
            return Collections.emptyList();
        }
    }
}

package com.helospark.lightdi.dependencywire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.definitioncollector.BeanDefinitionCollector;
import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.scanner.ClasspathScannerChain;

public class RecursiveDependencyDescriptorCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecursiveDependencyDescriptorCollector.class);

    private ClasspathScannerChain classpathScannerChain;
    private BeanDefinitionCollector beanDefinitionCollector;
    private ComponentScanCollector componentScanCollector;

    public RecursiveDependencyDescriptorCollector(ClasspathScannerChain classpathScannerChain,
            BeanDefinitionCollector beanDefinitionCollector,
            ComponentScanCollector componentScanCollector) {
        this.classpathScannerChain = classpathScannerChain;
        this.beanDefinitionCollector = beanDefinitionCollector;
        this.componentScanCollector = componentScanCollector;
    }

    public SortedSet<DependencyDescriptor> collectDependenciesUsingFullClasspathScan(String packageName) {
        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName(packageName)
                .withOnlyCurrentJar(false)
                .build();
        return collectDependenciesInternal(componentScanPackage);
    }

    public SortedSet<DependencyDescriptor> collectDependenciesUsingJarClasspathScan(String packageName, Class<?> jarRootClass) {
        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName(packageName)
                .withRootClass(jarRootClass)
                .withOnlyCurrentJar(true)
                .build();
        return collectDependenciesInternal(componentScanPackage);
    }

    private SortedSet<DependencyDescriptor> collectDependenciesInternal(ComponentScanPackage componentScanPackage) {
        SortedSet<DependencyDescriptor> dependencyDescriptors = new TreeSet<>();
        List<ComponentScanPackage> alreadyScannedPackages = new ArrayList<>();

        dependencyDescriptors = collectDepenenciesRecursively(componentScanPackage, alreadyScannedPackages);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Following dependency descriptors are annotated for LightDI: " + dependencyDescriptors);
        }
        return dependencyDescriptors;
    }

    public SortedSet<DependencyDescriptor> collectDependenciesStartingFromClass(Class<?> clazz) {
        return collectClasses(new ArrayList<>(), Collections.singletonList(clazz.getName()));
    }

    private SortedSet<DependencyDescriptor> collectDepenenciesRecursively(ComponentScanPackage packageName,
            List<ComponentScanPackage> alreadyScannerPackages) {
        if (!alreadyScannerPackages.contains(packageName)) {
            alreadyScannerPackages.add(packageName);

            List<String> classes = classpathScannerChain.scan(packageName);
            return collectClasses(alreadyScannerPackages, classes);
        } else {
            return new TreeSet<>();
        }
    }

    private SortedSet<DependencyDescriptor> collectClasses(List<ComponentScanPackage> alreadyScannerPackages, List<String> classes) {
        SortedSet<DependencyDescriptor> dependencyDescriptors = beanDefinitionCollector.collectDependencyDescriptors(classes);

        List<ComponentScanPackage> findOtherPackages = componentScanCollector.collectComponentScan(dependencyDescriptors);

        findOtherPackages.stream()
                .filter(scannedPackageName -> !alreadyScannerPackages.contains(scannedPackageName))
                .forEach(scannedPackageName -> dependencyDescriptors
                        .addAll(collectDepenenciesRecursively(scannedPackageName, alreadyScannerPackages)));

        return dependencyDescriptors;
    }
}

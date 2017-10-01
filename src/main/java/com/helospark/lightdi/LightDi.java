package com.helospark.lightdi;

public class LightDi {

    public LightDiContext initContextUsingFullClasspathScan(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public LightDiContext initContextUsing(String packageName, Class<?> jarReferenceClass) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackage(packageName, jarReferenceClass);
        return lightDiContext;
    }

    public LightDiContext initContextByClass(Class<?> clazz) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public LightDiContext initContextUsingFullClasspathScan(String packageName, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public LightDiContext initContextUsing(String packageName, Class<?> jarRelativeClass, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public LightDiContext initContextByClass(Class<?> clazz, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public int version() {
        return 1;
    }

}
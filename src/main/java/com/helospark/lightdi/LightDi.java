package com.helospark.lightdi;

public class LightDi {

    public static LightDiContext initContextUsingFullClasspathScan(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public static LightDiContext initContextUsing(String packageName, Class<?> jarReferenceClass) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackage(packageName, jarReferenceClass);
        return lightDiContext;
    }

    public static LightDiContext initContextByClass(Class<?> clazz) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public static LightDiContext initContextUsingFullClasspathScan(String packageName, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public static LightDiContext initContextUsing(String packageName, Class<?> jarRelativeClass, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromPackageUsingFullClasspathScan(packageName);
        return lightDiContext;
    }

    public static LightDiContext initContextByClass(Class<?> clazz, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public int version() {
        return 1;
    }

}
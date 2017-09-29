package com.helospark.lightdi;

public class LightDi {

    public LightDiContext initContextUsingFullClasspathScan(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackage(packageName);
        return lightDiContext;
    }

    public LightDiContext initContextUsing(String packageName, Class<?> jarRelativeClass) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromPackage(packageName);
        return lightDiContext;
    }

    public LightDiContext initContextByClass(Class<?> clazz) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public int version() {
        return 1;
    }

}
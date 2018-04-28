package com.helospark.lightdi;

public class LightDi {

    public static LightDiContext initContextByClass(Class<?> clazz) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public static LightDiContext initContextByClass(Class<?> clazz, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependenciesFromClass(clazz);
        return lightDiContext;
    }

    public static LightDiContext initContextByPackage(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependencyFromPackage(packageName);
        return lightDiContext;
    }

    public static LightDiContext initContextByPackage(String packageName, LightDiContextConfiguration configuration) {
        LightDiContext lightDiContext = new LightDiContext(configuration);
        lightDiContext.loadDependencyFromPackage(packageName);
        return lightDiContext;
    }

    public int version() {
        return 2;
    }

}
package com.helospark.lightdi;

public class LightDi {

    public LightDiContext initContextByPackage(String packageName) {
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
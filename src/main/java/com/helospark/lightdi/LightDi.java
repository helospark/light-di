package com.helospark.lightdi;

public class LightDi {

    public LightDiContext initContextByPackage(String packageName) {
        LightDiContext lightDiContext = new LightDiContext();
        lightDiContext.loadDependencies(packageName);
        return lightDiContext;
    }

}
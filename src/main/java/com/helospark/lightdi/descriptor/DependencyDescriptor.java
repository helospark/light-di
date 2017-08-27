package com.helospark.lightdi.descriptor;

import java.lang.reflect.Method;
import java.util.List;

public abstract class DependencyDescriptor implements InjectionDescriptor {
    protected Class<?> clazz;
    protected String qualifier;
    protected boolean isPrimary;

    protected List<Method> postConstructMethods;
    protected List<Method> preDestroyMethods;

    public Class<?> getClazz() {
        return clazz;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setPostConstructMethods(List<Method> postConstructMethods) {
        this.postConstructMethods = postConstructMethods;
    }

    public void setPreDestroyMethods(List<Method> preDestroyMethods) {
        this.preDestroyMethods = preDestroyMethods;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public List<Method> getPostConstructMethods() {
        return postConstructMethods;
    }

    public List<Method> getPreDestroyMethods() {
        return preDestroyMethods;
    }

    @Override
    public String toString() {
        return "DependencyDescriptor [clazz=" + clazz + ", qualifier=" + qualifier + ", isPrimary=" + isPrimary
                + ", postConstructMethods=" + postConstructMethods
                + ", preDestroyMethods=" + preDestroyMethods + "]";
    }

}

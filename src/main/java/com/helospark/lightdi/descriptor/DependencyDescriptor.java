package com.helospark.lightdi.descriptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public abstract class DependencyDescriptor implements InjectionDescriptor {
    protected Class<?> clazz;
    protected String qualifier;
    protected String scope;
    protected boolean isLazy = true;
    protected boolean isPrimary = false;

    protected List<Method> postConstructMethods = Collections.emptyList();
    protected List<Method> preDestroyMethods = Collections.emptyList();

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean isLazy) {
        this.isLazy = isLazy;
    }

    @Override
    public String toString() {
        return "DependencyDescriptor [clazz=" + clazz + ", qualifier=" + qualifier + ", scope=" + scope + ", isLazy=" + isLazy + ", isPrimary="
                + isPrimary + ", postConstructMethods=" + postConstructMethods + ", preDestroyMethods=" + preDestroyMethods + "]";
    }

    public boolean doesMatch(DependencyDescriptorQuery toFind) {
        boolean classMatch = toFind.getClazz()
                .map(clazz -> clazz.isAssignableFrom(this.getClazz()))
                .orElse(true);
        boolean qualifierMatch = toFind.getQualifier()
                .map(toFindQualifier -> this.getQualifier().equals(toFindQualifier))
                .orElse(true);
        return classMatch && qualifierMatch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + (isPrimary ? 1231 : 1237);
        result = prime * result + ((postConstructMethods == null) ? 0 : postConstructMethods.hashCode());
        result = prime * result + ((preDestroyMethods == null) ? 0 : preDestroyMethods.hashCode());
        result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DependencyDescriptor other = (DependencyDescriptor) obj;
        if (clazz == null) {
            if (other.clazz != null)
                return false;
        } else if (!clazz.equals(other.clazz))
            return false;
        if (isPrimary != other.isPrimary)
            return false;
        if (postConstructMethods == null) {
            if (other.postConstructMethods != null)
                return false;
        } else if (!postConstructMethods.equals(other.postConstructMethods))
            return false;
        if (preDestroyMethods == null) {
            if (other.preDestroyMethods != null)
                return false;
        } else if (!preDestroyMethods.equals(other.preDestroyMethods))
            return false;
        if (qualifier == null) {
            if (other.qualifier != null)
                return false;
        } else if (!qualifier.equals(other.qualifier))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        return true;
    }

}

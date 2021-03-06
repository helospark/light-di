package com.helospark.lightdi.descriptor;

import static java.util.Optional.empty;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;
import com.helospark.lightdi.util.LightDiAnnotation;

/**
 * Descriptor represents all parameters known about an class instance dependency.
 * @author helospark
 *
 */
public abstract class DependencyDescriptor implements InjectionDescriptor, Comparable<DependencyDescriptor> {
    protected Class<?> clazz;
    protected Type genericType;
    protected String qualifier;
    protected String scope;
    protected boolean isLazy = true;
    protected boolean isPrimary = false;
    protected int order = LightDiConstants.DEFAULT_ORDER;

    protected List<Method> postConstructMethods = Collections.emptyList();
    protected List<Method> preDestroyMethods = Collections.emptyList();
    protected List<MethodDescriptor> setterDescriptor;
    protected List<FieldDescriptor> fieldDescriptor;

    protected List<DependencyCondition> conditions = Collections.emptyList();

    protected boolean initalizationFinished = false;
    protected Optional<Class<?>> importingClass = empty();

    protected Set<LightDiAnnotation> mergedAnnotations = Collections.emptySet();

    public void setInitalizationFinished(boolean initalizationFinished) {
        this.initalizationFinished = initalizationFinished;
    }

    public boolean isInitalizationFinished() {
        return initalizationFinished;
    }

    public Optional<Type> getGenericType() {
        return Optional.ofNullable(genericType);
    }

    public Optional<Class<?>> getImportingClass() {
        return importingClass;
    }

    public void setImportingClass(Optional<Class<?>> importingClass) {
        this.importingClass = importingClass;
    }

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

    public List<DependencyCondition> getConditions() {
        return conditions;
    }

    public int getOrder() {
        return order;
    }

    public void setSetterDescriptor(List<MethodDescriptor> setterDescriptor) {
        this.setterDescriptor = setterDescriptor;
    }

    public void setFieldDescriptor(List<FieldDescriptor> fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public Set<LightDiAnnotation> getMergedAnnotations() {
        return mergedAnnotations;
    }

    public List<MethodDescriptor> getSetterDescriptor() {
        return setterDescriptor;
    }

    public List<FieldDescriptor> getFieldDescriptor() {
        return fieldDescriptor;
    }

    @Override
    public String toString() {
        return "DependencyDescriptor [clazz=" + clazz + ", genericType=" + genericType + ", qualifier=" + qualifier + ", scope=" + scope + ", isLazy=" + isLazy + ", isPrimary=" + isPrimary
                + ", order=" + order + ", postConstructMethods=" + postConstructMethods + ", preDestroyMethods=" + preDestroyMethods + ", setterDescriptor=" + setterDescriptor + ", fieldDescriptor="
                + fieldDescriptor + ", conditions=" + conditions + ", initalizationFinished=" + initalizationFinished + ", importingClass=" + importingClass + ", mergedAnnotations="
                + mergedAnnotations + "]";
    }

    public boolean doesMatch(DependencyDescriptorQuery toFind) {
        return qualifierMatch(toFind) && classMatch(toFind);
    }

    private Boolean classMatch(DependencyDescriptorQuery toFind) {
        Optional<Class<?>> classToCheck = toFind.getClazz();
        if (classToCheck.isPresent()) {
            Class<?> notNullClass = classToCheck.get();
            // separated to avoid native call in most cases with simple reference check
            return notNullClass.equals(this.clazz) || notNullClass.isAssignableFrom(this.clazz);
        } else {
            return true;
        }
    }

    private Boolean qualifierMatch(DependencyDescriptorQuery toFind) {
        // .map is omitted for performance reason
        Optional<String> qualifier = toFind.getQualifier();
        if (qualifier.isPresent()) {
            return qualifier.get().equals(this.qualifier);
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + order;
        result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
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
        if (order != other.order)
            return false;
        if (qualifier == null) {
            if (other.qualifier != null)
                return false;
        } else if (!qualifier.equals(other.qualifier))
            return false;
        return true;
    }

    @Override
    public int compareTo(DependencyDescriptor other) {
        int orderDiff = this.order - other.order;
        if (this.equals(other)) {
            return 0;
        } else if (orderDiff != 0) {
            return orderDiff;
        } else {
            int classCompareResult = this.clazz.getName().compareTo(other.clazz.getName());
            if (classCompareResult != 0) {
                return classCompareResult;
            }
            return qualifier.compareTo(other.qualifier);
        }
    }

}

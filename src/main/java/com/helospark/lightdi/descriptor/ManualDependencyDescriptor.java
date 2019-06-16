package com.helospark.lightdi.descriptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Generated;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.util.LightDiAnnotation;

/**
 * Represents a dependency that was manually registered using registerSingleton method on the context.
 * @author helospark
 */
public class ManualDependencyDescriptor extends DependencyDescriptor {

    @Generated("SparkTools")
    private ManualDependencyDescriptor(Builder builder) {
        this.clazz = builder.clazz;
        this.qualifier = builder.qualifier;
        this.scope = builder.scope;
        this.isLazy = builder.isLazy;
        this.isPrimary = builder.isPrimary;
        this.order = builder.order;
        this.postConstructMethods = builder.postConstructMethods;
        this.preDestroyMethods = builder.preDestroyMethods;
        this.conditions = builder.conditions;
        this.initalizationFinished = builder.initalizationFinished;
        this.importingClass = builder.importingClass;
        this.mergedAnnotations = builder.mergedAnnotations;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Class<?> clazz;
        private String qualifier;
        private String scope;
        private boolean isLazy;
        private boolean isPrimary;
        private int order;
        private List<Method> postConstructMethods = Collections.emptyList();
        private List<Method> preDestroyMethods = Collections.emptyList();
        private List<DependencyCondition> conditions = Collections.emptyList();
        private boolean initalizationFinished;
        private Optional<Class<?>> importingClass = Optional.empty();
        private Set<LightDiAnnotation> mergedAnnotations = Collections.emptySet();

        private Builder() {
        }

        public Builder withClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder withQualifier(String qualifier) {
            this.qualifier = qualifier;
            return this;
        }

        public Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder withIsLazy(boolean isLazy) {
            this.isLazy = isLazy;
            return this;
        }

        public Builder withIsPrimary(boolean isPrimary) {
            this.isPrimary = isPrimary;
            return this;
        }

        public Builder withOrder(int order) {
            this.order = order;
            return this;
        }

        public Builder withPostConstructMethods(List<Method> postConstructMethods) {
            this.postConstructMethods = postConstructMethods;
            return this;
        }

        public Builder withPreDestroyMethods(List<Method> preDestroyMethods) {
            this.preDestroyMethods = preDestroyMethods;
            return this;
        }

        public Builder withConditions(List<DependencyCondition> conditions) {
            this.conditions = conditions;
            return this;
        }

        public Builder withInitalizationFinished(boolean initalizationFinished) {
            this.initalizationFinished = initalizationFinished;
            return this;
        }

        public Builder withImportingClass(Optional<Class<?>> importingClass) {
            this.importingClass = importingClass;
            return this;
        }

        public Builder withMergedAnnotations(Set<LightDiAnnotation> mergedAnnotations) {
            this.mergedAnnotations = mergedAnnotations;
            return this;
        }

        public ManualDependencyDescriptor build() {
            return new ManualDependencyDescriptor(this);
        }
    }

}

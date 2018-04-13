package com.helospark.lightdi.dependencywire.chain.support.domain;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import javax.annotation.Generated;

/**
 * Similar to Java 8 Parameter class, but compatible with Java 7.
 * @author helospark
 */
public class CustomParameter {
    private AnnotatedElement annotatedParameterTypes;
    private Class<?> parameterType;
    private Type genericType;

    @Generated("SparkTools")
    private CustomParameter(Builder builder) {
        this.annotatedParameterTypes = builder.annotatedParameterTypes;
        this.parameterType = builder.parameterType;
        this.genericType = builder.genericType;
    }

    public AnnotatedElement getAnnotatedParameterTypes() {
        return annotatedParameterTypes;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public Type getGenericType() {
        return genericType;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private AnnotatedElement annotatedParameterTypes;
        private Class<?> parameterType;
        private Type genericType;

        private Builder() {
        }

        public Builder withAnnotatedParameterTypes(AnnotatedElement annotatedParameterTypes) {
            this.annotatedParameterTypes = annotatedParameterTypes;
            return this;
        }

        public Builder withParameterType(Class<?> parameterType) {
            this.parameterType = parameterType;
            return this;
        }

        public Builder withGenericType(Type genericType) {
            this.genericType = genericType;
            return this;
        }

        public CustomParameter build() {
            return new CustomParameter(this);
        }
    }

}

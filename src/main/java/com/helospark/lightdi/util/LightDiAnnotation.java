package com.helospark.lightdi.util;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;

public class LightDiAnnotation {
    private Annotation type;
    private Map<String, Object> attributes;

    @Generated("SparkTools")
    private LightDiAnnotation(Builder builder) {
        this.type = builder.type;
        this.attributes = builder.attributes;
    }

    public Annotation getType() {
        return type;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttributeValue(String key) {
        return getAttributeAs(key, Object.class);
    }

    public <T> T getAttributeAs(String key, Class<T> type) {
        Optional<Object> attribute = Optional.ofNullable(attributes.get(key));
        if (attribute.isPresent()) {
            Object obj = attribute.get();
            if (type.isAssignableFrom(obj.getClass())) {
                return type.cast(obj);
            } else {
                throw new IllegalStateException("Attribute " + key + " is type " + obj.getClass() + " but " + type + " is expected");
            }
        } else {
            throw new IllegalArgumentException("Attribute " + key + " cannot be found on " + type);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof LightDiAnnotation)) {
            return false;
        }
        LightDiAnnotation castOther = (LightDiAnnotation) other;
        return Objects.equals(type, castOther.type) && Objects.equals(attributes, castOther.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, attributes);
    }

    @Override
    public String toString() {
        return "LightDiAnnotation [type=" + type + ", attributes=" + attributes + "]";
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Annotation type;
        private Map<String, Object> attributes = Collections.emptyMap();

        private Builder() {
        }

        public Builder withType(Annotation type) {
            this.type = type;
            return this;
        }

        public Builder withAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        public LightDiAnnotation build() {
            return new LightDiAnnotation(this);
        }
    }

}

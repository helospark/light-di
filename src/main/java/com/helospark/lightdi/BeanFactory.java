package com.helospark.lightdi;

import javax.annotation.Generated;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.MethodInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;

public class BeanFactory {
    private ConstructorInvoker constructorInvoker;
    private SetterInvoker setterInvoker;
    private FieldSetInvoker fieldSetInvoker;
    private MethodInvoker methodInvoker;
    private PostConstructInvoker postConstructInvoker;

    @Generated("SparkTools")
    private BeanFactory(Builder builder) {
        this.constructorInvoker = builder.constructorInvoker;
        this.setterInvoker = builder.setterInvoker;
        this.fieldSetInvoker = builder.fieldSetInvoker;
        this.methodInvoker = builder.methodInvoker;
        this.postConstructInvoker = builder.postConstructInvoker;
    }

    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        try {
            if (dependencyToCreate instanceof StereotypeDependencyDescriptor) {
                StereotypeDependencyDescriptor stereotypeDependency = (StereotypeDependencyDescriptor) dependencyToCreate;
                Object result = constructorInvoker.invokeConstructor(lightDiContext, stereotypeDependency);
                setterInvoker.invokeSetters(lightDiContext, stereotypeDependency, result);
                fieldSetInvoker.setFieldValues(lightDiContext, stereotypeDependency, result);
                postConstructInvoker.invokePostConstructMethods(stereotypeDependency, result);
                return result;
            } else {
                BeanDependencyDescriptor beanDependencyDescriptor = (BeanDependencyDescriptor) dependencyToCreate;
                Object configurationBean = lightDiContext
                        .getBean(beanDependencyDescriptor.getConfigurationDescriptor());
                return methodInvoker.invokeMethod(lightDiContext, beanDependencyDescriptor.getMethodDescriptor(),
                        configurationBean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private ConstructorInvoker constructorInvoker;
        private SetterInvoker setterInvoker;
        private FieldSetInvoker fieldSetInvoker;
        private MethodInvoker methodInvoker;
        private PostConstructInvoker postConstructInvoker;

        private Builder() {
        }

        public Builder withConstructorInvoker(ConstructorInvoker constructorInvoker) {
            this.constructorInvoker = constructorInvoker;
            return this;
        }

        public Builder withSetterInvoker(SetterInvoker setterInvoker) {
            this.setterInvoker = setterInvoker;
            return this;
        }

        public Builder withFieldSetInvoker(FieldSetInvoker fieldSetInvoker) {
            this.fieldSetInvoker = fieldSetInvoker;
            return this;
        }

        public Builder withMethodInvoker(MethodInvoker methodInvoker) {
            this.methodInvoker = methodInvoker;
            return this;
        }

        public Builder withPostConstructInvoker(PostConstructInvoker postConstructInvoker) {
            this.postConstructInvoker = postConstructInvoker;
            return this;
        }

        public BeanFactory build() {
            return new BeanFactory(this);
        }
    }

}

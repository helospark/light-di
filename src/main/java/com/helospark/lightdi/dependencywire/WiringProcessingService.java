package com.helospark.lightdi.dependencywire;

import java.lang.reflect.Method;
import java.util.List;

import com.helospark.lightdi.annotation.PostConstruct;
import com.helospark.lightdi.annotation.PreDestroy;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.constructor.ConstructorDescriptor;
import com.helospark.lightdi.descriptor.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.setter.SetterDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class WiringProcessingService {
    private ConstructorWireSupport constructorWireSupport;
    private SetterWireSupport setterWireSupport;
    private FieldWireSupport fieldWireSupport;

    public WiringProcessingService(ConstructorWireSupport constructorWireSupport, SetterWireSupport setterWireSupport,
            FieldWireSupport fieldWireSupport) {
        this.constructorWireSupport = constructorWireSupport;
        this.setterWireSupport = setterWireSupport;
        this.fieldWireSupport = fieldWireSupport;
    }

    public void wireTogetherDependencies(List<DependencyDescriptor> dependencyDescriptors) {
        dependencyDescriptors.stream()
                .forEach(dependency -> collectDependencies(dependencyDescriptors, dependency));
    }

    private void collectDependencies(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptor dependency) {
        Class<?> clazz = dependency.getClazz();
        List<ConstructorDescriptor> constructorDependencies = constructorWireSupport
                .getConstructors(dependencyDescriptors, clazz);
        List<SetterDescriptor> setterDependencies = setterWireSupport.getSetterDependencies(clazz,
                dependencyDescriptors);
        List<FieldDescriptor> fieldDescriptors = fieldWireSupport.getFieldDependencies(clazz, dependencyDescriptors);

        List<Method> postConstructMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PostConstruct.class);
        List<Method> preDestroyMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PreDestroy.class);

        dependency.setConstructorDescriptor(constructorDependencies);
        dependency.setSetterDescriptor(setterDependencies);
        dependency.setFieldDescriptor(fieldDescriptors);
        dependency.setPostConstructMethods(postConstructMethods);
        dependency.setPreDestroyMethods(preDestroyMethods);
    }
}

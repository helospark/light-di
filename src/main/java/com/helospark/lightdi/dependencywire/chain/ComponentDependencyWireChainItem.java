package com.helospark.lightdi.dependencywire.chain;

import java.util.List;

import com.helospark.lightdi.dependencywire.chain.support.ConstructorWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.FieldWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.SetterWireSupport;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.constructor.ConstructorDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;

public class ComponentDependencyWireChainItem implements DependencyWireChain {
    private ConstructorWireSupport constructorWireSupport;
    private SetterWireSupport setterWireSupport;
    private FieldWireSupport fieldWireSupport;

    public ComponentDependencyWireChainItem(ConstructorWireSupport constructorWireSupport,
            SetterWireSupport setterWireSupport, FieldWireSupport fieldWireSupport) {
        this.constructorWireSupport = constructorWireSupport;
        this.setterWireSupport = setterWireSupport;
        this.fieldWireSupport = fieldWireSupport;
    }

    @Override
    public void collectDependencies(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptor dependencyDescriptor) {
        if (canHandle(dependencyDescriptor)) {
            handleInternal(dependencyDescriptors, dependencyDescriptor);
        }
    }

    private boolean canHandle(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof StereotypeDependencyDescriptor;
    }

    private void handleInternal(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptor dependencyDescriptor) {
        StereotypeDependencyDescriptor dependency = (StereotypeDependencyDescriptor) dependencyDescriptor;

        Class<?> clazz = dependency.getClazz();
        List<ConstructorDescriptor> constructorDependencies = constructorWireSupport
                .getConstructors(dependencyDescriptors, clazz);
        List<MethodDescriptor> setterDependencies = setterWireSupport.getSetterDependencies(clazz,
                dependencyDescriptors);
        List<FieldDescriptor> fieldDescriptors = fieldWireSupport.getFieldDependencies(clazz,
                dependencyDescriptors);

        dependency.setConstructorDescriptor(constructorDependencies);
        dependency.setSetterDescriptor(setterDependencies);
        dependency.setFieldDescriptor(fieldDescriptors);
    }

}

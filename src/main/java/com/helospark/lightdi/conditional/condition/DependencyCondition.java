package com.helospark.lightdi.conditional.condition;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface DependencyCondition {

    public boolean evaluate(LightDiContext context, List<DependencyDescriptor> dependencies);

}

package com.helospark.lightdi.conditional.condition;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public interface DependencyCondition {

    public boolean evaluate(LightDiContext context, SortedSet<DependencyDescriptor> dependencies);

}

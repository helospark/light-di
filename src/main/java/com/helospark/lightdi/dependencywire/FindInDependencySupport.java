package com.helospark.lightdi.dependencywire;

import java.util.List;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.util.DependencyChooser;

public class FindInDependencySupport {

    public DependencyDescriptor findOrThrow(List<DependencyDescriptor> dependencyDescriptors,
            DependencyDescriptorQuery query) {
        return DependencyChooser.findDependencyFromQuery(dependencyDescriptors, query);
    }
}

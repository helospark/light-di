package com.helospark.lightdi.dependencywire.chain.support.chain;

import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.SpecialValueTypeInjectionDescriptor;
import com.helospark.lightdi.specialinject.InjectionPoint;

public class SpecialValueInjectDescriptorBuilderChainItem implements InjectDescriptorBuilderChainItem {
    private static List<Class<?>> SUPPORTED_SPECIAL_VALUE_TYPES = Arrays.asList(InjectionPoint.class);

    @Override
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request) {
        return SpecialValueTypeInjectionDescriptor.builder()
                .withClazz(request.getType())
                .build();
    }

    @Override
    public boolean doesSupport(InjectDescriptorBuilderRequest request) {
        return SUPPORTED_SPECIAL_VALUE_TYPES.stream()
                .filter(supportedType -> request.getType().equals(supportedType))
                .findFirst()
                .isPresent();
    }

}

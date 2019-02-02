package com.helospark.lightdi.it;

import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.util.Collections;

import org.junit.Test;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;
import com.helospark.lightdi.it.primarymanualoverride.ManualPrimaryOverrideContext;
import com.helospark.lightdi.it.primarymanualoverride.SecondImplementation;

public class ManualPrimaryOverrideIT {

    @Test
    public void testContextShouldInitializeWithOverriddenPrimaryBean() {
        // GIVEN
        LightDiContext context = new LightDiContext();

        ManualDependencyDescriptor dependencyDescriptor = ManualDependencyDescriptor.builder()
                .withClazz(SecondImplementation.class)
                .withIsLazy(false)
                .withIsPrimary(true)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(SecondImplementation.class))
                .withScope(LightDiConstants.SCOPE_SINGLETON)
                .build();

        context.registerSingleton(dependencyDescriptor, new SecondImplementation());
        context.loadDependencies(Collections.emptyList(), Collections.singletonList(ManualPrimaryOverrideContext.class));

        // WHEN
        ManualPrimaryOverrideContext result = context.getBean(ManualPrimaryOverrideContext.class);

        // THEN throws
        assertThat(result.getRootBean(), instanceOf(SecondImplementation.class));
    }


}

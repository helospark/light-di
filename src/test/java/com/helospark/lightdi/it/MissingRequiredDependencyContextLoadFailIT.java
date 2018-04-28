package com.helospark.lightdi.it;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.it.requireddependencyfail.BeanWithNonExistentRequiredConstructorDependency;
import com.helospark.lightdi.it.requireddependencyfail.BeanWithNonExistentRequiredFieldDependency;
import com.helospark.lightdi.it.requireddependencyfail.BeanWithNonExistentRequiredSetterDependency;
import com.helospark.lightdi.it.requireddependencyfail.BeanWithNonExistentValueInField;

public class MissingRequiredDependencyContextLoadFailIT {

    @Test(expected = ContextInitializationFailedException.class)
    public void testLoadContextWithMissingRequiredFieldDependencyShouldFail() {
        // GIVEN

        // WHEN
        LightDi lightDi = new LightDi();
        LightDi.initContextByClass(BeanWithNonExistentRequiredFieldDependency.class);

        // THEN throws
    }

    @Test(expected = ContextInitializationFailedException.class)
    public void testLoadContextWithMissingRequiredSetterDependencyShouldFail() {
        // GIVEN

        // WHEN
        LightDi lightDi = new LightDi();
        LightDi.initContextByClass(BeanWithNonExistentRequiredSetterDependency.class);

        // THEN throws
    }

    @Test(expected = ContextInitializationFailedException.class)
    public void testLoadContextWithMissingRequiredConstructorDependencyShouldFail() {
        // GIVEN

        // WHEN
        LightDi lightDi = new LightDi();
        LightDi.initContextByClass(BeanWithNonExistentRequiredConstructorDependency.class);

        // THEN throws
    }

    @Test(expected = BeanCreationException.class)
    public void testBeanInitializationShouldFailWithMissingValueAnnotation() {
        // GIVEN
        LightDi lightDi = new LightDi();
        LightDiContext context = LightDi.initContextByClass(BeanWithNonExistentValueInField.class);

        // WHEN
        context.getBean(BeanWithNonExistentValueInField.class);

        // THEN throws
    }

}

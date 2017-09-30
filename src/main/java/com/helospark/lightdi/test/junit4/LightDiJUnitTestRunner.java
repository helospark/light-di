package com.helospark.lightdi.test.junit4;

import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.constants.LightDiConstants;
import com.helospark.lightdi.descriptor.ManualDependencyDescriptor;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.MockBean;
import com.helospark.lightdi.test.annotation.SpyBean;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.AutowirePostProcessor;
import com.helospark.lightdi.util.ReflectionUtil;

public class LightDiJUnitTestRunner extends BlockJUnit4ClassRunner {
    private Class<?> clazz;
    private LightDiContext context;

    public LightDiJUnitTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        this.clazz = klass;
    }

    @Override
    protected Object createTest() throws Exception {
        Object result = super.createTest();
        String packageName = extractPackageFromClass(clazz);
        context = new LightDiContext();

        preinitializeMocks(result);

        context.loadDependenciesFromPackage(packageName);
        AutowirePostProcessor autowireSupportUtil = context.getAutowireSupportUtil();
        autowireSupportUtil.autowireFieldsTo(result);
        return result;
    }

    private void preinitializeMocks(Object result) {
        registerMockBeans(result, context);
        registerSpyBeans(result, context);
    }

    private void registerMockBeans(Object instance, LightDiContext context) {
        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> AnnotationUtil.hasAnnotation(field, MockBean.class))
                .forEach(field -> registerAndAutowire(instance, context, field, clazz -> Mockito.mock(clazz)));
    }

    private void registerSpyBeans(Object instance, LightDiContext context) {
        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> AnnotationUtil.hasAnnotation(field, SpyBean.class))
                .forEach(field -> registerAndAutowire(instance, context, field, clazz -> Mockito.spy(clazz)));
    }

    private void registerAndAutowire(Object instance, LightDiContext context, Field field, Function<Class<?>, Object> mockFactory) {
        Class<?> classToCreate = field.getType();
        ManualDependencyDescriptor dependencyDescriptor = ManualDependencyDescriptor.builder()
                .withClazz(classToCreate)
                .withIsLazy(false)
                .withIsPrimary(true)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(classToCreate))
                .withScope(LightDiConstants.SCOPE_SINGLETON)
                .build();
        Object mock = mockFactory.apply(classToCreate);
        context.registerSingleton(dependencyDescriptor, mock);
        ReflectionUtil.injectToField(field, instance, mock);
    }

    private String extractPackageFromClass(Class<?> clazz) {
        LightDiTest annotation = AnnotationUtil.getSingleAnnotationOfType(clazz, LightDiTest.class);
        if (annotation == null) {
            throw new IllegalStateException(
                    LightDiJUnitTestRunner.class.getSimpleName() + " is running a test which does not contain @LightDiTest annotation");
        }
        return annotation.rootPackage();
    }
}

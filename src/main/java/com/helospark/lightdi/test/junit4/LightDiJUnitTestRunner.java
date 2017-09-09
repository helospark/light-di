package com.helospark.lightdi.test.junit4;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.test.LightDiTest;
import com.helospark.lightdi.util.AutowirePostProcessor;

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
        context = new LightDi().initContextByPackage(packageName);
        AutowirePostProcessor autowireSupportUtil = context.getAutowireSupportUtil();
        autowireSupportUtil.autowireFieldsTo(result);
        return result;
    }

    private String extractPackageFromClass(Class<?> clazz) {
        LightDiTest annotation = clazz.getAnnotation(LightDiTest.class);
        if (annotation == null) {
            throw new IllegalStateException(
                    LightDiJUnitTestRunner.class.getSimpleName() + " is running a test which does not contain @LightDiTest annotation");
        }
        return annotation.rootPackage();
    }
}

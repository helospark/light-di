package com.helospark.lightdi.scanner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.scanner.SimpleJarScanner;
import com.helospark.lightdi.scanner.support.ClassPathToClassNameConverter;
import com.helospark.lightdi.scanner.support.ClassToSourceConverter;

public class SimpleJarScannerIT {
    private static final Class<?> JAR_FILE_ROOT_PACKAGE = SimpleJarScannerIT.class;
    private SimpleJarScanner simpleJarScanner;

    @Mock
    private ClassToSourceConverter classToSourceConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        simpleJarScanner = new SimpleJarScanner(new ClassPathToClassNameConverter(), classToSourceConverter);
    }

    @Test
    public void testShouldReadExistingJarFile() {
        // GIVEN
        File file = new File(this.getClass().getResource("/jartest.jar").getFile());
        when(classToSourceConverter.geSourceLocationName(JAR_FILE_ROOT_PACKAGE)).thenReturn(file);
        ComponentScanPackage toScan = ComponentScanPackage.builder()
                .withOnlyCurrentJar(true)
                .withPackageName("com.helospark")
                .withRootClass(JAR_FILE_ROOT_PACKAGE)
                .build();

        // WHEN
        List<String> result = simpleJarScanner.scan(toScan);

        // THEN
        assertTrue(simpleJarScanner.doesSupport(toScan));
        assertTrue(result.contains("com.helospark.SecondPackageInComHelosparkPackage"));
        assertTrue(result.contains("com.helospark.TopLevelClassInTestClass"));
        assertTrue(result.contains("com.helospark.TestClassInComHelosparkPackage$InnerClassOfTestClass"));
        assertTrue(result.contains("com.helospark.TestClassInComHelosparkPackage"));
        assertThat(result.size(), is(4));
    }
}

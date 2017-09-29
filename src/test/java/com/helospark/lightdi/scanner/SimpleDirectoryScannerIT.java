package com.helospark.lightdi.scanner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;
import com.helospark.lightdi.scanner.support.ClassPathToClassNameConverter;
import com.helospark.lightdi.scanner.support.ClassToSourceConverter;

public class SimpleDirectoryScannerIT {
    private static final Class<?> JAR_FILE_ROOT_PACKAGE = SimpleDirectoryScannerIT.class;

    private SimpleDirectoryScanner simpleDirectoryScanner;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        simpleDirectoryScanner = new SimpleDirectoryScanner(new ClassPathToClassNameConverter(), new ClassToSourceConverter());
    }

    @Test
    public void testShouldReadExistingJarFile() {
        // GIVEN
        ComponentScanPackage toScan = ComponentScanPackage.builder()
                .withOnlyCurrentJar(true)
                .withPackageName("com.helospark.lightdi.scanner.directorytoscan")
                .withRootClass(JAR_FILE_ROOT_PACKAGE)
                .build();

        // WHEN
        List<String> result = simpleDirectoryScanner.scan(toScan);

        // THEN
        assertTrue(simpleDirectoryScanner.doesSupport(toScan));
        assertTrue(result.contains("com.helospark.lightdi.scanner.directorytoscan.DirectoryRootPackageForTest"));
        assertTrue(result.contains("com.helospark.lightdi.scanner.directorytoscan.OtherClassInDirectoryToScanLocation"));
        assertTrue(result.contains("com.helospark.lightdi.scanner.directorytoscan.DirectoryRootPackageForTest$InnerClass"));
        assertTrue(result.contains("com.helospark.lightdi.scanner.directorytoscan.TopLevelClassInOtherClassFile"));
        assertThat(result.size(), is(4));
    }
}

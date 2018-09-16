package com.helospark.lightdi.scanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

public class PreprocessedAnnotationScannerChainItemTest {
    private static final String ROOT_LOCATION = "/fakeclasspathroot/";
    private static final String FAKE_PREPROCESSED_FILE = ROOT_LOCATION + "preprocessed";
    private static final String ANOTHER_FAKE_PREPROCESSED_FILE = ROOT_LOCATION + "anotherpreprocessed";

    @Mock
    private PreprocessedFileLocationProvider preprocessedFileLocationProvider;
    @Mock
    private StreamFactory streamFactory;

    private PreprocessedAnnotationScannerChainItem underTest;

    @Before
    public void setUp() {
        initMocks(this);
        underTest = new PreprocessedAnnotationScannerChainItem(preprocessedFileLocationProvider, streamFactory);
        given(streamFactory.stream(any(Collection.class))).willAnswer(ads -> ((Collection<?>) ads.getArguments()[0]).stream());
    }

    @Test
    public void testClasspathOpen() {
        // GIVEN

        // WHEN
        InputStream result = this.getClass().getResourceAsStream(FAKE_PREPROCESSED_FILE);

        // THEN
        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void testDoesSupport() {
        // GIVEN
        given(preprocessedFileLocationProvider.getFileList())
                .willReturn(Collections.singleton(FAKE_PREPROCESSED_FILE));

        // WHEN
        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName("com.helospark.test")
                .build();
        boolean result = underTest.doesSupport(componentScanPackage);

        // THEN
        assertThat(result, is(true));
    }

    @Test
    public void testScan() {
        // GIVEN
        given(preprocessedFileLocationProvider.getFileList())
                .willReturn(Collections.singleton(FAKE_PREPROCESSED_FILE));

        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName("com.helospark.test")
                .build();

        // WHEN
        List<String> result = underTest.scan(componentScanPackage);

        // THEN
        assertThat(result, containsInAnyOrder("com.helospark.test.DummyClass", "com.helospark.test.FakeClass"));
    }

    @Test
    public void testNotExistingFileShouldNotFail() {
        // GIVEN
        Set<String> files = new HashSet<>();
        files.add(FAKE_PREPROCESSED_FILE);
        files.add("/tmp/doesnotexist");
        given(preprocessedFileLocationProvider.getFileList())
                .willReturn(files);

        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName("com.helospark.test")
                .build();

        // WHEN
        List<String> result = underTest.scan(componentScanPackage);

        // THEN
        assertThat(result.size(), is(2));
    }

    @Test
    public void testCache() {
        // GIVEN
        given(preprocessedFileLocationProvider.getFileList())
                .willReturn(Collections.singleton(FAKE_PREPROCESSED_FILE));

        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName("com.helospark.test")
                .build();

        // WHEN
        underTest.scan(componentScanPackage);
        underTest.scan(componentScanPackage);
        underTest.scan(componentScanPackage);
        underTest.scan(componentScanPackage);

        // THEN
        verify(preprocessedFileLocationProvider, times(1)).getFileList();
    }

    @Test
    public void testMultipleFiles() {
        // GIVEN
        Set<String> files = new HashSet<>();
        files.add(FAKE_PREPROCESSED_FILE);
        files.add(ANOTHER_FAKE_PREPROCESSED_FILE);
        given(preprocessedFileLocationProvider.getFileList())
                .willReturn(files);

        ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                .withPackageName("com.helospark.test")
                .build();

        // WHEN
        List<String> result = underTest.scan(componentScanPackage);

        // THEN
        assertThat(result.contains("com.helospark.test.AnotherFile"), is(true));
    }

    @Test
    public void testExternalFile() throws IOException {
        // GIVEN
        File temp = File.createTempFile("preprocessed_", Long.toString(System.nanoTime()));
        try {
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write("com.helospark.test.ExternalFile".getBytes(UTF_8));
            fos.close();

            Set<String> files = new HashSet<>();
            files.add(FAKE_PREPROCESSED_FILE);
            files.add(temp.getAbsolutePath());
            given(preprocessedFileLocationProvider.getFileList())
                    .willReturn(files);

            ComponentScanPackage componentScanPackage = ComponentScanPackage.builder()
                    .withPackageName("com.helospark.test")
                    .build();

            // WHEN
            List<String> result = underTest.scan(componentScanPackage);

            // THEN
            assertThat(result.contains("com.helospark.test.ExternalFile"), is(true));
        } finally {
            temp.deleteOnExit();
        }
    }

}

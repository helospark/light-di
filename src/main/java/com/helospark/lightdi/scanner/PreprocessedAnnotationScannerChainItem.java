package com.helospark.lightdi.scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.annotationpreprocessor.AnnotationProcessor;
import com.helospark.lightdi.common.StreamFactory;
import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

public class PreprocessedAnnotationScannerChainItem implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreprocessedAnnotationScannerChainItem.class);

    private PreprocessedFileLocationProvider preprocessedFileLocationProvider;
    private StreamFactory streamFactory;
    private LightDiContextConfiguration contextConfiguration;

    private Set<String> resourceFileContent;
    private Object resourceFileContentLock = new Object();

    public PreprocessedAnnotationScannerChainItem(PreprocessedFileLocationProvider preprocessedFileLocationProvider, StreamFactory streamFactory, LightDiContextConfiguration contextConfiguration) {
        this.preprocessedFileLocationProvider = preprocessedFileLocationProvider;
        this.streamFactory = streamFactory;
        this.contextConfiguration = contextConfiguration;
    }

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " using preprocessed file");

        return readFile().stream()
                .filter(className -> doesPackageMatch(componentScanPackage, className))
                .collect(Collectors.toList());
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        return contextConfiguration.isUseComponentScanFile() && !readFile().isEmpty();
    }

    private boolean doesPackageMatch(ComponentScanPackage componentScanPackage, String className) {
        return className.indexOf(componentScanPackage.getPackageName()) == 0;
    }

    private Set<String> readFile() {
        if (resourceFileContent == null) {
            synchronized (resourceFileContentLock) {
                if (resourceFileContent == null) {
                    Set<String> fileList = preprocessedFileLocationProvider.getFileList();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Reading files " + fileList);
                    }
                    resourceFileContent = streamFactory.stream(fileList)
                            .flatMap(file -> readFileContent(file).stream())
                            .collect(Collectors.toSet());
                }

            }
        }
        return resourceFileContent;
    }

    private Set<String> readFileContent(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Error loading file " + fileName, e);
        }
        if (inputStream != null) {
            String result = readStream(inputStream);
            Set<String> allPackages = Arrays.asList(result.split(AnnotationProcessor.SEPARATOR))
                    .stream()
                    .filter(element -> !element.isEmpty())
                    .collect(Collectors.toSet());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Preprocessed file " + fileName + " contains: " + allPackages);
            }
            return allPackages;
        } else {
            return Collections.emptySet();
        }
    }

    private InputStream getInputStream(String fileName) throws FileNotFoundException {
        // TODO: Could there be a better solution?
        File file = new File(fileName);
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            return this.getClass().getResourceAsStream(fileName);
        }
    }

    private String readStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return new String(baos.toByteArray(), UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.helospark.lightdi.scanner;

import static com.helospark.lightdi.annotationpreprocessor.AnnotationProcessor.RESOURCE_FILE_NAME;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreprocessedFileLocationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreprocessedFileLocationProvider.class);

    private static final String PREPROCESSED_FILE_RELATIVE_LOCATION = "/resources/lightdi/" + RESOURCE_FILE_NAME;

    private ClasspathProvider classpathProvider;

    public PreprocessedFileLocationProvider(ClasspathProvider classpathProvider) {
        this.classpathProvider = classpathProvider;
    }

    public Set<String> getFileList() {
        List<String> classpath = classpathProvider.provideClasspathEntries();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Classpath is " + classpath);
        }
        Set<String> result = classpath
                .stream()
                .filter(path -> isFolder(path))
                .map(path -> path + PREPROCESSED_FILE_RELATIVE_LOCATION)
                .collect(Collectors.toSet());
        result.add(PREPROCESSED_FILE_RELATIVE_LOCATION); // always check file in the current classpath's resource location
        return result;
    }

    private boolean isFolder(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

}

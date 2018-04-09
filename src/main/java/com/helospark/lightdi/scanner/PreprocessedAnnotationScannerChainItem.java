package com.helospark.lightdi.scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.annotationpreprocessor.AnnotationProcessor;
import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

public class PreprocessedAnnotationScannerChainItem implements ClasspathScannerChainItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreprocessedAnnotationScannerChainItem.class);

    @Override
    public List<String> scan(ComponentScanPackage componentScanPackage) {
        LOGGER.debug("Scanning " + componentScanPackage + " using preprocessed file");

        InputStream inputStream = getInputStream();
        String result = readStream(inputStream);
        return Arrays.asList(result.split(AnnotationProcessor.SEPARATOR))
                .stream()
                .filter(element -> !element.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public boolean doesSupport(ComponentScanPackage componentScanPackage) {
        return componentScanPackage.isOnlyCurrentJar() && getInputStream() != null;
    }

    private InputStream getInputStream() {
        return this.getClass().getResourceAsStream("/resources/lightdi/preprocessed");
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

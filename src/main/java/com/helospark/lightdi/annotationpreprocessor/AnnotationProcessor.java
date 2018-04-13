package com.helospark.lightdi.annotationpreprocessor;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Service;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {
    public static final String RESOURCE_FILE_NAME = "preprocessed";
    public static final String RESOURCE_FOLDER = "resources.lightdi";
    public static final String SEPARATOR = "\n";
    private Elements elementUtils;
    private Filer filer;

    private HashSet<String> foundClasses;
    private Writer writer;

    @Override
    public synchronized void init(ProcessingEnvironment environment) {
        super.init(environment);
        this.elementUtils = environment.getElementUtils();
        this.filer = environment.getFiler();

        // Need cache, because of multiple passes annotation processor might do
        foundClasses = new HashSet<>();
        writer = null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment environment) {
        try {

            if (writer == null) {
                FileObject newFile = filer.createResource(StandardLocation.CLASS_OUTPUT, RESOURCE_FOLDER, RESOURCE_FILE_NAME);
                writer = new OutputStreamWriter(newFile.openOutputStream(), UTF_8);
            }

            for (Class<? extends Annotation> clazz : supportedAnnotations()) {
                Set<? extends Element> annotatedElements = environment.getElementsAnnotatedWith(clazz);
                for (Element element : annotatedElements) {
                    if (element instanceof TypeElement) {
                        Name name = elementUtils.getBinaryName((TypeElement) element);
                        String toBeAppended = name.toString();
                        if (!foundClasses.contains(toBeAppended)) {
                            writer.append(toBeAppended + SEPARATOR);
                        }
                        foundClasses.add(toBeAppended);
                    }
                }
            }
            writer.flush();
            // TODO: where to close writer?
        } catch (Throwable exception) {
            exception.printStackTrace(System.out);
            throw new RuntimeException(exception);
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotations().stream()
                .map(annotation -> annotation.getName())
                .collect(Collectors.toSet());
    }

    public Set<Class<? extends Annotation>> supportedAnnotations() {
        return new HashSet<>(Arrays.asList(Component.class, Service.class, Configuration.class));
    }

}

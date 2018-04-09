package com.helospark.lightdi.android;

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
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Service;

public class AnnotationProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment arg0) {
        super.init(arg0);
        this.elementUtils = arg0.getElementUtils();
        this.filer = arg0.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment environment) {
        System.out.println("########################### Processing annotations");
        try {
            Set<? extends Element> elrf = environment.getElementsAnnotatedWithAny(supportedAnnotations());

            FileObject newFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "com.helospark.lightdi", "preprocessed");
            Writer writer = newFile.openWriter();

            for (Element e : elrf) {
                if (e instanceof TypeElement) {
                    Name name = elementUtils.getBinaryName((TypeElement) e);
                    String toBeAppended = name.toString() + "\n";
                    System.out.println(toBeAppended);
                    writer.append(toBeAppended);
                }
            }
            writer.close();
        } catch (Throwable e1) {
            e1.printStackTrace(System.out);
            e1.printStackTrace();
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println("########################### Checking annotations");
        return supportedAnnotations().stream()
                .map(annotation -> annotation.getName())
                .collect(Collectors.toSet());
    }

    public Set<Class<? extends Annotation>> supportedAnnotations() {
        return new HashSet<>(Arrays.asList(Component.class, Service.class, Configuration.class));
    }

}

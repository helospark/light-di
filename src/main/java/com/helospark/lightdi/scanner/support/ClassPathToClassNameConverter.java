package com.helospark.lightdi.scanner.support;

public class ClassPathToClassNameConverter {

    public String pathToClass(String fullPath) {
        return fullPath.replaceAll("/", ".")
                .replaceAll("\\.class", "");
    }

}

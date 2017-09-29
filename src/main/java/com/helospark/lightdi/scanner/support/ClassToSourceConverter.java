package com.helospark.lightdi.scanner.support;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class ClassToSourceConverter {

    public File geSourceLocationName(Class<?> clazz) {
        ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URL location = codeSource.getLocation();
        String filePath = location.getFile();
        return new File(filePath);
    }
}

package com.helospark.lightdi.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesFileLoader {

    public Map<String, String> load(String name) {
        if (name.startsWith("classpath:")) {
            return loadFromClasspath(name.replaceFirst("classpath:", ""));
        } else if (name.startsWith("file:")) {
            return loadFromFile(name.replaceFirst("file:", ""));
        } else {
            throw new IllegalArgumentException("No file named " + name);
        }
    }

    private Map<String, String> loadFromClasspath(String fileName) {
        try {
            InputStream input = PropertiesFileLoader.class.getClassLoader().getResourceAsStream(fileName);
            if (input == null) {
                throw new IllegalArgumentException("No such file " + fileName);
            }
            return loadPropertiesInternal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> loadFromFile(String fileName) {
        try (InputStream is = new FileInputStream(fileName)) {
            return loadPropertiesInternal(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> loadPropertiesInternal(InputStream input) throws IOException {
        Properties properties = new Properties();
        properties.load(input);

        return properties.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()),
                        entry -> String.valueOf(entry.getValue())));
    }
}

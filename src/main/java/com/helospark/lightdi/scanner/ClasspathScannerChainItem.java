package com.helospark.lightdi.scanner;

import java.util.List;

import com.helospark.lightdi.dependencywire.domain.ComponentScanPackage;

public interface ClasspathScannerChainItem {

    public List<String> scan(ComponentScanPackage componentScanPackage);

    public boolean doesSupport(ComponentScanPackage componentScanPackage);
}

package com.helospark.lightdi.dependencywire;

public class WiringProcessingServiceFactory {
    private ConstructorWireSupport constructorWireSupport;
    private SetterWireSupport setterWireSupport;
    private FieldWireSupport fieldWireSupport;
    private FindInDependencySupport findInDependencySupport;

    public WiringProcessingServiceFactory() {
        findInDependencySupport = new FindInDependencySupport();
        PropertyDescriptorFactory propertyDescriptorFactory = new PropertyDescriptorFactory();
        constructorWireSupport = new ConstructorWireSupport(findInDependencySupport, propertyDescriptorFactory);
        setterWireSupport = new SetterWireSupport(findInDependencySupport, propertyDescriptorFactory);
        fieldWireSupport = new FieldWireSupport(findInDependencySupport, propertyDescriptorFactory);
    }

    public WiringProcessingService createFieldWireSupport() {
        return new WiringProcessingService(constructorWireSupport, setterWireSupport, fieldWireSupport);
    }
}

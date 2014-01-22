package ru.org.icad.mishka.app;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class OrderCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) {
        descriptor.setShouldOrderMappings(false);

    }
}

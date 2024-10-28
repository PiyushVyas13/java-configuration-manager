package com.piyushvyas.cms.reader;

public class ReaderFactory {
    public static ConfigurationReader createReader(String extension) {
        return switch (extension) {
            case "properties" -> new PropertiesReader();
            case "yaml" -> new YamlReader();
            case "toml" -> throw new UnsupportedOperationException("Support for yaml not added yet.");
            default -> throw new IllegalArgumentException("Invalid extension: " + extension);
        };
    }
}

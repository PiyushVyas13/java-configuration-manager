package com.piyushvyas.cms.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.nio.file.Files;

public class PropertiesReader implements ConfigurationReader{
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Map<String, String> read(String path) throws IOException {
        Properties properties = new Properties();

        try (InputStream in = Files.newInputStream(Path.of(path))){
            properties.load(in);
        }
        return new HashMap<>((Map) properties);
    }
}

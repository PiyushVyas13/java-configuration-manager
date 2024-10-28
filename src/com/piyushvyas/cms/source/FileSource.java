package com.piyushvyas.cms.source;

import com.piyushvyas.cms.reader.ConfigurationReader;
import com.piyushvyas.cms.reader.ReaderFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileSource implements ConfigurationSource {
    private String filePath;
    private final ConfigurationReader reader;
    private final Map<String, String> configurations;

    public FileSource(String filePath) {
        this.filePath = filePath;
        String extension = getFileExtension(filePath);
        this.reader = ReaderFactory.createReader(extension);
        this.configurations = new HashMap<>();
        refreshConfigs();
    }

    private void refreshConfigs() {
        try {
            configurations.clear();
            configurations.putAll(reader.read(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String filePath) {
        int i = filePath.lastIndexOf('.');
        if (i == -1) {
            throw new IllegalArgumentException("Invalid filepath: " + filePath);
        }
        return filePath.substring(i + 1);
    }


    @Override
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(configurations.get(key));
    }

    @Override
    public void setValue(String key, String value) {

    }

    @Override
    public String getName() {
        return "fileSource";
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

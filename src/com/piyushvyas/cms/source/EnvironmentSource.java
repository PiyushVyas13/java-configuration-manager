package com.piyushvyas.cms.source;

import java.util.Optional;

public class EnvironmentSource implements ConfigurationSource {


    @Override
    public Optional<String> getValue(String key) {
        String value = System.getenv(key);
        return Optional.ofNullable(value);
    }

    @Override
    public void setValue(String key, String value) {
        throw new UnsupportedOperationException("Cannot set environment variables programmatically.");
    }

    @Override
    public String getName() {
        return "environmentSource";
    }
}

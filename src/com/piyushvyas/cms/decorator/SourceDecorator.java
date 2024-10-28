package com.piyushvyas.cms.decorator;

import com.piyushvyas.cms.source.ConfigurationSource;

import java.util.Optional;

public abstract class SourceDecorator implements ConfigurationSource {
    protected final ConfigurationSource source;

    public SourceDecorator(ConfigurationSource source) {
        this.source = source;
    }

    @Override
    public void setValue(String key, String value) {
        source.setValue(key, value);
    }

    @Override
    public Optional<String> getValue(String key) {
        return source.getValue(key);
    }

    public abstract String getName();
}

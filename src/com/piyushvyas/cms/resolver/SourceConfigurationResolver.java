package com.piyushvyas.cms.resolver;

import com.piyushvyas.cms.source.ConfigurationSource;

import java.util.Optional;

public class SourceConfigurationResolver extends ConfigurationResolver {
    private final ConfigurationSource source;

    public SourceConfigurationResolver(ConfigurationSource source) {
        this.source = source;
    }

    @Override
    public Optional<String> resolve(String key) {
        Optional<String> value = source.getValue(key);

        if(value.isPresent()) {
            return value;
        }

        if(nextResolver == null) {
            return Optional.empty();
        }

        return nextResolver.resolve(key);
    }
}

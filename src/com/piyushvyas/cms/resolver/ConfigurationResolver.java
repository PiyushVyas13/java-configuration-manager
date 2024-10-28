package com.piyushvyas.cms.resolver;

import java.util.Optional;

public abstract class ConfigurationResolver {
    protected ConfigurationResolver nextResolver;

    public abstract Optional<String> resolve(String key);

    public void setNextResolver(ConfigurationResolver nextResolver) {
        this.nextResolver = nextResolver;
    }
}

package com.piyushvyas.cms.source;

import java.util.Optional;

public interface ConfigurationSource {
    Optional<String> getValue(String key);
    void setValue(String key, String value);
    String getName();
}

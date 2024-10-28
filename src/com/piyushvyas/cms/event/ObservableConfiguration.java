package com.piyushvyas.cms.event;

public interface ObservableConfiguration {
    void addListener(String key, ConfigurationChangeListener listener);
    void removeListener(String key, ConfigurationChangeListener listener);
    void notifyListener(String key, ConfigurationChangeEvent event);
}

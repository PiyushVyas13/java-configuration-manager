package com.piyushvyas.cms.event;

import java.time.LocalDateTime;

public class ConfigurationChangeEvent {
    private final String key;
    private final String oldValue;
    private final String newValue;
    private final String sourceName;
    private final LocalDateTime timestamp;
    private final ChangeType changeType;

    public String getKey() {
        return key;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getSourceName() {
        return sourceName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public enum ChangeType {
        ADDED, MODIFIED, REMOVED
    }

    public ConfigurationChangeEvent(String key, ChangeType changeType, String oldValue, String newValue, String sourceName) {
        this.key = key;
        this.oldValue = oldValue;
        this.changeType = changeType;
        this.newValue = newValue;
        this.sourceName = sourceName;
        this.timestamp = LocalDateTime.now();
    }


}

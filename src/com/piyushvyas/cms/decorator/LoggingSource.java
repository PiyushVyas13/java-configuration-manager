package com.piyushvyas.cms.decorator;

import com.piyushvyas.cms.source.ConfigurationSource;

import java.util.Optional;
import java.util.logging.Logger;

public class LoggingSource extends SourceDecorator {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public LoggingSource(ConfigurationSource source) {
        super(source);
    }


    @Override
    public Optional<String> getValue(String key) {
        logger.info("getting value for key: " + key);
        Optional<String> value = super.getValue(key);
        logger.info("value for key: " + key + ": " + value.orElse("<not-found>"));
        return value;
    }

    @Override
    public void setValue(String key, String value) {
        logger.info("setting value for key: " + key + ": " + value);
        super.setValue(key, value);
    }

    @Override
    public String getName() {
        return "loggingSource";
    }
}

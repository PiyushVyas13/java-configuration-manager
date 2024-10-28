package com.piyushvyas.cms.manager;

import com.piyushvyas.cms.event.ConfigurationChangeEvent;
import com.piyushvyas.cms.event.ConfigurationChangeListener;
import com.piyushvyas.cms.event.ObservableConfiguration;
import com.piyushvyas.cms.resolver.SourceConfigurationResolver;
import com.piyushvyas.cms.source.ConfigurationSource;

import java.util.*;

public class ConfigurationManager implements ObservableConfiguration {
    private final List<ConfigurationSource> sources;
    private SourceConfigurationResolver resolver;
    private static ConfigurationManager instance;
    private final Map<String, List<ConfigurationChangeListener>> listeners;


    private ConfigurationManager() {
        sources = new ArrayList<>();
        listeners = new HashMap<>();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void addSource(ConfigurationSource source) {
        sources.add(source);
    }

    public void removeSource(ConfigurationSource source) {
        sources.remove(source);
    }

    public Optional<String> getValue(String key) {
        if(resolver == null) {
            resolver = setupResolvers();
        }

        return resolver.resolve(key);
    }

    public void setValue(String key, String value, String sourceId) {
        String oldValue = getValue(key).orElse(null);

        Optional<ConfigurationSource> configSource = sources.
                stream()
                .filter(source -> source.getName().equals(sourceId))
                .findFirst();

        if(configSource.isEmpty()) {
            throw new IllegalArgumentException("No configuration source found with id " + sourceId);
        }

        ConfigurationSource source = configSource.get();
        source.setValue(key, value);
        notifyListener(key, new ConfigurationChangeEvent(key, oldValue == null ? ConfigurationChangeEvent.ChangeType.ADDED : ConfigurationChangeEvent.ChangeType.MODIFIED, oldValue, value, sourceId));

    }

    private SourceConfigurationResolver setupResolvers() {
        if(sources.isEmpty()) {
            throw new RuntimeException("Cannot setup resolvers without a source.");
        }

        List<SourceConfigurationResolver> resolvers = new ArrayList<>();

        for(ConfigurationSource source : sources) {
            resolvers.add(new SourceConfigurationResolver(source));
        }

        for(int i=0; i<resolvers.size()-2; i++) {
            resolvers.get(i).setNextResolver(resolvers.get(i+1));
        }

        return resolvers.get(0);
    }

    @Override
    public void addListener(String key, ConfigurationChangeListener listener) {
        if(!listeners.containsKey(key)) {
            listeners.put(key, new ArrayList<>());
        }
        listeners.get(key).add(listener);
    }

    @Override
    public void removeListener(String key, ConfigurationChangeListener listener) {
        if(listeners.containsKey(key)) {
            listeners.get(key).remove(listener);
        }
    }

    @Override
    public void notifyListener(String key, ConfigurationChangeEvent event) {
        if(listeners.containsKey(key)) {
            listeners.get(key)
                    .forEach(listener -> listener.onConfigurationChanged(event));
        }
    }
}

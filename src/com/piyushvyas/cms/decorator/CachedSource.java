package com.piyushvyas.cms.decorator;

import com.piyushvyas.cms.source.ConfigurationSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachedSource extends SourceDecorator {
    private static final int CACHE_EXPIRY_MS = 10 * 60 * 1000;

    private final Map<String, String> cache;
    private final Map<String, Long> timestamps;
    private int cacheExpiryMs;


    public CachedSource(ConfigurationSource source) {
       this(source, CACHE_EXPIRY_MS);
    }

    public CachedSource(ConfigurationSource source, int cacheExpiryMs) {
        super(source);
        this.cache = new HashMap<>();
        this.timestamps = new HashMap<>();
        this.cacheExpiryMs = cacheExpiryMs;
    }

    @Override
    public Optional<String> getValue(String key) {
        if(presentInCache(key)) {
            return Optional.ofNullable(cache.get(key));
        }

        Optional<String> value = source.getValue(key);
        value.ifPresent(s -> updateCache(key, s));
        return value;
    }

    @Override
    public void setValue(String key, String value) {
        source.setValue(key, value);
        updateCache(key, value);
    }

    @Override
    public String getName() {
        return "cachedConfigurationSource";
    }

    public void setCacheExpiryMs(int cacheExpiryMs) {
        this.cacheExpiryMs = cacheExpiryMs;
    }

    public void invalidateKey(String key) {
        cache.remove(key);
        timestamps.remove(key);
    }

    public void invalidateCache() {
        cache.clear();
        timestamps.clear();
    }

    public boolean presentInCache(String key) {
        if(!cache.containsKey(key) || !timestamps.containsKey(key)) {
            return false;
        }

        Long timestamp = timestamps.get(key);

        if(timestamp == null) {
            return false;
        }

        if(System.currentTimeMillis() - timestamp > cacheExpiryMs) {
            invalidateCache();
            return false;
        }

        return true;
    }

    private void updateCache(String key, String value) {
        cache.put(key, value);
        timestamps.put(key, System.currentTimeMillis());
    }
}

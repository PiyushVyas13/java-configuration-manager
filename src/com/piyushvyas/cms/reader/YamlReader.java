package com.piyushvyas.cms.reader;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class YamlReader implements ConfigurationReader{

    @Override
    public Map<String, String> read(String path) throws IOException {
        Yaml yaml = new Yaml();

        try (InputStream in = Files.newInputStream(Path.of(path))){
            Map<String, Object> nestedMap = yaml.load(in);
            Map<String, String> map = new HashMap<>();
            flatMapRec("", nestedMap, map);
            return map;
        }
    }

    @SuppressWarnings("unchecked")
    private void flatMapRec(String prefix, Map<String, Object> map, Map<String, String> flatMap) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
           String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();

           if(entry.getValue() instanceof Map) {
               flatMapRec(key, (Map<String, Object>) entry.getValue(), flatMap);
           } else {
               flatMap.put(key, entry.getValue() != null ? entry.getValue().toString() : null);
           }
        }
    }
}

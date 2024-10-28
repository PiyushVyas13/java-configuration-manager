package com.piyushvyas.cms.reader;

import java.io.IOException;
import java.util.Map;

public interface ConfigurationReader {
    Map<String, String> read(String path) throws IOException;
    /* TODO: Implement write operation */
}

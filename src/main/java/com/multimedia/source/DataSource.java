package com.multimedia.source;

import java.io.IOException;
import java.net.URI;

public interface DataSource extends Source {
    /**
     * open source
     */
    void open(URI uri) throws IOException;

    /**
     * close source
     */
    void close() throws IOException;
}

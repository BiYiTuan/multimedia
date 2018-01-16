package com.multimedia.sink;

import java.io.IOException;
import java.net.URI;

public interface DataSink extends Sink {
    /**
     * open sink
     */
    void open(URI uri) throws IOException;

    /**
     * close sink
     */
    void close() throws IOException;
}

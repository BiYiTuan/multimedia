package com.multimedia.source;

import java.io.IOException;
import java.net.URI;

public interface DataSource extends Source {
    /**
     * open source
     */
    void open(URI uri) throws IOException;

    /**
     * get source's size
     */
    long getSize();

    /**
     * seek to target position
     */
    void seek(long position) throws IOException;

    /**
     * close source
     */
    void close() throws IOException;
}

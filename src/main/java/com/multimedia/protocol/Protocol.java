package com.multimedia.protocol;

import java.io.IOException;
import java.net.URL;

public interface Protocol {
    /**
     * open
     */
    void open(URL url) throws IOException;

    /**
     * read data
     */
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * write data
     */
    void write(byte[] buffer, int offset, int length) throws IOException;

    /**
     * seek to target position
     */
    void seek(long position) throws IOException;

    /**
     * close
     */
    void close() throws IOException;
}

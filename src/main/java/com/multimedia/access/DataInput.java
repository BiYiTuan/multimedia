package com.multimedia.access;

import java.io.IOException;

public interface DataInput {
    /**
     * open
     */
    long open(long offset) throws IOException;

    /**
     * read data
     */
    int read(byte[] buffer) throws IOException;

    /**
     * read data
     */
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * close
     */
    void close();
}

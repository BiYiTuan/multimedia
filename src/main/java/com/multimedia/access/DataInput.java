package com.multimedia.access;

import java.io.IOException;

public interface DataInput {
    /**
     * open
     */
    void open() throws IOException;

    /**
     * get total size
     */
    long size();

    /**
     * read data
     */
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * get read position
     */
    long offset();

    /**
     * seek to target position
     */
    void seek(long position) throws IOException;

    /**
     * close
     */
    void close();
}

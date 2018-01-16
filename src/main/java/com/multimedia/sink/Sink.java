package com.multimedia.sink;

import java.io.IOException;

public interface Sink {
    /**
     * write data to sink
     */
    void write(byte[] buffer, int offset, int length) throws IOException;
}

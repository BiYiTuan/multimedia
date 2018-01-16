package com.multimedia.source;

import java.io.IOException;

public interface Source {
    /**
     * read data from souce
     */
    int read(byte[] buffer, int offset, int length) throws IOException;
}

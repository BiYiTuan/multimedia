package com.multimedia.sink;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;

public class FileDataSink implements DataSink {
    private RandomAccessFile mFile;

    public void open(URI uri) throws IOException {
        mFile = new RandomAccessFile(uri.getPath(), "rw");
    }

    public void close() throws IOException {
        mFile.close();
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid write length");
        }

        if (length > 0) {
            mFile.write(buffer, offset, length);
        }
    }
}

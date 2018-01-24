package com.multimedia.protocol;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public final class FileProtocol implements Protocol {
    private RandomAccessFile mFile;

    public FileProtocol() {
        //nothing
    }

    @Override
    public void open(URL url) throws IOException {
        mFile = new RandomAccessFile(url.toString(), "rw");
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (length == 0) {
            return 0;
        }
        else {
            return mFile.read(buffer, offset, length);
        }
    }

    @Override
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

    @Override
    public void seek(long position) throws IOException {
        if (position < 0 || position >= mFile.length()) {
            throw new IllegalArgumentException("invalid file position");
        }

        mFile.seek(position);
    }

    @Override
    public void close() throws IOException {
        if (mFile != null) {
            mFile.close();
        }
    }
}

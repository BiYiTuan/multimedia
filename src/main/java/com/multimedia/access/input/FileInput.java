package com.multimedia.access.input;

import com.multimedia.access.DataInput;

import java.io.IOException;
import java.io.RandomAccessFile;

public final class FileInput implements DataInput {
    private String mPath;

    private RandomAccessFile mFile = null;

    public FileInput(String path) {
        mPath = path;
    }

    @Override
    public long open(long offset) throws IOException {
        mFile = new RandomAccessFile(mPath, "r");

        if (offset < 0 || offset >= mFile.length()) {
            throw new IllegalArgumentException("invalid offset");
        }

        if (offset > 0) {
            mFile.seek(offset);
        }

        return mFile.length() - offset;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length <= 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        return mFile.read(buffer, offset, length);
    }

    @Override
    public void close() {
        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        try {
            mFile.close();
        }
        catch (IOException e) {
            //ignore
        }
    }

    private boolean isOpen() {
        return mFile != null;
    }
}

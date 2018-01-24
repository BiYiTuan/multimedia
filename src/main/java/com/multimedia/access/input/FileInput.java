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
    public void open() throws IOException {
        mFile = new RandomAccessFile(mPath, "r");
    }

    @Override
    public long size() {
        long fileSize = -1;

        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        try {
            fileSize = mFile.length();
        }
        catch (IOException e) {
            //ignore
        }

        return fileSize;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        if (length == 0) {
            return 0;
        }
        else {
            return mFile.read(buffer, offset, length);
        }
    }

    @Override
    public long offset() {
        long position = -1;

        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        try {
            position = mFile.getFilePointer();
        }
        catch (IOException e) {
            //ignore
        }

        return position;
    }

    @Override
    public void seek(long position) throws IOException {
        if (position < 0 || position >= size()) {
            throw new IllegalArgumentException("invalid file position");
        }

        if (!isOpen()) {
            throw new IllegalStateException("not open yet");
        }

        if (position != offset()) {
            mFile.seek(position);
        }
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

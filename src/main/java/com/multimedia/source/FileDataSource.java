package com.multimedia.source;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;

public class FileDataSource implements DataSource {
    private RandomAccessFile mFile;
    private long mFileSize;

    public void open(URI uri) throws IOException {
        mFile = new RandomAccessFile(uri.getPath(), "r");
        mFileSize = mFile.length();
    }

    public long getSize() {
        return mFileSize;
    }

    public void seek(long position) throws IOException {
        if (position < 0 || position >= mFileSize) {
            throw new IllegalArgumentException("invalid file position");
        }

        mFile.seek(position);
    }

    public void close() throws IOException {
        mFile.close();
    }

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
            long filePointer = mFile.getFilePointer();
            if (filePointer == mFileSize) {
                /**
                 * end of file
                 */
                return -1;
            }

            int readBytes = (int)Math.min(mFileSize - filePointer, (long)length);

            return mFile.read(buffer, offset, readBytes);
        }
    }
}

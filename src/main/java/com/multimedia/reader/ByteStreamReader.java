package com.multimedia.reader;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class ByteStreamReader {
    private byte[] mBuffer;
    private int mOffset;
    private int mLimit;

    public ByteStreamReader(byte[] buffer) {
        this(buffer, 0);
    }

    public ByteStreamReader(byte[] buffer, int offset) {
        this(buffer, offset, buffer.length - offset);
    }

    public ByteStreamReader(byte[] buffer, int offset, int length) {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid data length");
        }

        mBuffer = buffer;
        mOffset = offset;
        mLimit = offset + length;
    }

    /**
     * the number of bytes can be read
     */
    public int available() {
        if (mOffset < mLimit) {
            return mLimit - mOffset;
        }
        else {
            return 0;
        }
    }

    /**
     * skip number of bytes
     */
    public void skipBytes(int numBytes) {
        if (numBytes < 0 || numBytes > available()) {
            throw new IllegalArgumentException("invalid number of bytes");
        }

        mOffset += numBytes;
    }

    /**
     * read unsigned int8
     */
    public int readUnsignedInt8() {
        return (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read unsigned int16 in big endian
     */
    public int readUnsignedInt16() {
        return (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read unsigned int24 in big endian
     */
    public int readUnsignedInt24() {
        return (mBuffer[mOffset++] & 0xff) << 16
                | (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read unsigned int32 in big endian
     */
    public long readUnsignedInt32() {
        return (mBuffer[mOffset++] & 0xffL) << 24
                | (mBuffer[mOffset++] & 0xffL) << 16
                | (mBuffer[mOffset++] & 0xffL) << 8
                | (mBuffer[mOffset++] & 0xffL);
    }

    /**
     * read unsigned int16 in little endian
     */
    public int readUnsignedInt16InLittleEndian() {
        return (mBuffer[mOffset++] & 0xff)
                | (mBuffer[mOffset++] & 0xff) << 8;
    }

    /**
     * read unsigned int32 in little endian.
     */
    public long readUnsignedInt32InLittleEndian() {
        return (mBuffer[mOffset++] & 0xffL)
                | (mBuffer[mOffset++] & 0xffL) << 8
                | (mBuffer[mOffset++] & 0xffL) << 16
                | (mBuffer[mOffset++] & 0xffL) << 24;
    }

    /**
     * read float
     */
    public float readFloat() {
        int value = (mBuffer[mOffset++] & 0xff) << 24
                | (mBuffer[mOffset++] & 0xff) << 16
                | (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);

        return Float.intBitsToFloat(value);
    }

    /**
     * read double
     */
    public double readDouble() {
        long value = (mBuffer[mOffset++] & 0xffL) << 56
                | (mBuffer[mOffset++] & 0xffL) << 48
                | (mBuffer[mOffset++] & 0xffL) << 40
                | (mBuffer[mOffset++] & 0xffL) << 32
                | (mBuffer[mOffset++] & 0xffL) << 24
                | (mBuffer[mOffset++] & 0xffL) << 16
                | (mBuffer[mOffset++] & 0xffL) << 8
                | (mBuffer[mOffset++] & 0xffL);

        return Double.longBitsToDouble(value);
    }

    /**
     * read UTF-8 string
     */
    public String readUTF8String(int length) {
        String value = new String(mBuffer, mOffset, length, Charset.forName("UTF-8"));

        mOffset += length;

        return value;
    }

    /**
     * read bit stream
     */
    public BitStreamReader readBits(int length) {
        BitStreamReader reader = new BitStreamReader(mBuffer, mOffset, length);

        mOffset += length;

        return reader;
    }

    /**
     * read bytes
     */
    public byte[] readBytes(int length) {
        byte[] value = Arrays.copyOfRange(mBuffer, mOffset, length);

        mOffset += length;

        return value;
    }
}

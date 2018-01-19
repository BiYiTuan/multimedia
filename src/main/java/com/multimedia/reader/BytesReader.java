package com.multimedia.reader;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class BytesReader {
    private byte[] mBuffer;
    private int mOffset;
    private int mLimit;

    public BytesReader(byte[] buffer) {
        this(buffer, 0);
    }

    public BytesReader(byte[] buffer, int offset) {
        this(buffer, offset, buffer.length - offset);
    }

    public BytesReader(byte[] buffer, int offset, int length) {
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
     * read one byte as unsigned value
     */
    public int readUnsignedByte() {
        return (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read two bytes as unsigned value
     */
    public int readUnsignedShort() {
        return (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read two bytes as unsigned value in little endian
     */
    public int readUnsignedShortInLittleEndian() {
        return (mBuffer[mOffset++] & 0xff)
                | (mBuffer[mOffset++] & 0xff) << 8;
    }

    /**
     * read three bytes as unsigned value
     */
    public int readUnsignedInt24() {
        return (mBuffer[mOffset++] & 0xff) << 16
                | (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);
    }

    /**
     * read four bytes as unsigned value.
     */
    public long readUnsignedInt() {
        return (mBuffer[mOffset++] & 0xffL) << 24
                | (mBuffer[mOffset++] & 0xffL) << 16
                | (mBuffer[mOffset++] & 0xffL) << 8
                | (mBuffer[mOffset++] & 0xffL);
    }

    /**
     * read four bytes as unsigned value in little endian.
     */
    public long readUnsignedIntInLittleEndian() {
        return (mBuffer[mOffset++] & 0xffL)
                | (mBuffer[mOffset++] & 0xffL) << 8
                | (mBuffer[mOffset++] & 0xffL) << 16
                | (mBuffer[mOffset++] & 0xffL) << 24;
    }

    /**
     * read four bytes as float value
     */
    public float readFloat() {
        int value = (mBuffer[mOffset++] & 0xff) << 24
                | (mBuffer[mOffset++] & 0xff) << 16
                | (mBuffer[mOffset++] & 0xff) << 8
                | (mBuffer[mOffset++] & 0xff);

        return Float.intBitsToFloat(value);
    }

    /**
     * read eight bytes as double value
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
     * read bytes as UTF-8 string
     */
    public String readUTF8String(int length) {
        String value = new String(mBuffer, mOffset, length, Charset.forName("UTF-8"));

        mOffset += length;

        return value;
    }

    /**
     * read bytes as bits stream
     */
    public BitsReader readBits(int length) {
        return new BitsReader(mBuffer, mOffset, length);
    }

    /**
     * read bytes as data
     */
    public byte[] readData(int length) {
        byte[] value = Arrays.copyOfRange(mBuffer, mOffset, length);

        mOffset += length;

        return value;
    }
}

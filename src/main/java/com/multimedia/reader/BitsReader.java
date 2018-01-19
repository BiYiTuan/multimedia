package com.multimedia.reader;

public final class BitsReader {
    private static final int BITS_PER_BYTE = 8;

    private byte[] mBuffer;
    private int mOffset;
    private int mLimit;

    private int mAvailableBitsInByte;

    public BitsReader(byte[] buffer) {
        this(buffer, 0);
    }

    public BitsReader(byte[] buffer, int offset) {
        this(buffer, offset, buffer.length - offset);
    }

    public BitsReader(byte[] buffer, int offset, int length) {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid data length");
        }

        mBuffer = buffer;
        mOffset = offset;
        mLimit = offset + length;

        mAvailableBitsInByte = BITS_PER_BYTE;
    }

    /**
     * get the number of bits yet can be read
     */
    public int available() {
        if (mOffset < mLimit) {
            return mAvailableBitsInByte +
                    (mLimit - mOffset - 1) * BITS_PER_BYTE;
        }
        else {
            return 0;
        }
    }

    /**
     * skip one bit
     */
    private void skipBit() {
        if (--mAvailableBitsInByte == 0) {
            if (++mOffset < mLimit) {
                mAvailableBitsInByte = BITS_PER_BYTE;
            }
        }
    }

    /**
     * skip number of bits
     */
    public void skipBits(int numBits) {
        if (numBits < 0 || numBits > available()) {
            throw new IllegalArgumentException("invalid number of bits");
        }

        while (numBits > 0) {
            skipBit();

            numBits--;
        }
    }

    /**
     * read one bit
     */
    private int readBit() {
        int value = (mBuffer[mOffset] >> (mAvailableBitsInByte - 1)) & 0x01;

        skipBit();

        return value;
    }

    /**
     * read up to 63 bits (there is no unsigned type in java)
     */
    public long readBits(int numBits) {
        if (numBits < 0 || numBits > available() || numBits > 63) {
            throw new IllegalArgumentException("invalid number of bits");
        }

        long value = 0;

        while (numBits > 0) {
            value = (value << 1) | readBit();

            numBits--;
        }

        return value;
    }
}

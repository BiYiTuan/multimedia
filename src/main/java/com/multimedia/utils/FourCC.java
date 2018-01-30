package com.multimedia.utils;

public final class FourCC {
    public static final int FLV = convert("FLV");

    private FourCC() {
        //nothing
    }

    private static int convert(String fourcc) {
        int length = fourcc.length();
        if (length > 4) {
            throw new IllegalArgumentException("must equal or less than 4 letter");
        }

        int code = 0;

        for (int i = 0; i < length; i++) {
            code <<= 8;
            code |= fourcc.charAt(i);
        }

        return code;
    }
}

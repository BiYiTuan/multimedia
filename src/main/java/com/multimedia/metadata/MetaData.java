package com.multimedia.metadata;

import java.util.HashMap;
import java.util.Map;

public final class MetaData {
    /**
     * video
     */
    public static final String KEY_WIDTH = "width";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_FRAME_RATE = "frame_rate";
    public static final String KEY_COLOR_SPACE = "color_space";
    public static final String KEY_ROTATE_DEGREE = "rotate_degree";

    /**
     * audio
     */
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_SAMPLE_RATE = "sample_rate";
    public static final String KEY_SMAPLE_SIZE = "sample_size";

    /**
     * video/audio codec
     */
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_BITRATE = "bitrate";
    public static final String KEY_I_FRAME_INTERVAL = "I_frame_interval";
    public static final String KEY_QUALITY = "quality";

    /**
     * id3
     */
    public static final String KEY_ALBUM = "album";
    public static final String KEY_ALBUM_ART = "album_art";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_COMPOSER = "composer";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_TITLE = "title";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DATE = "date";
    public static final String KEY_WRITER = "writer";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_RATING = "rating";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_COPYRIGHT = "copyright";

    /**
     * streaming
     */
    public static final String KEY_BANDWIDTH = "bandwidth";


    private Map<String, Object> mTable;

    public MetaData() {
        mTable = new HashMap<String, Object>();
    }

    public MetaData putString(String key, String value) {
        mTable.put(key, value);

        return this;
    }

    public String getString(String key, String defaultValue) {
        if (!mTable.containsKey(key)) {
            return defaultValue;
        }

        return (String)mTable.get(key);
    }

    public MetaData putLong(String key, Long value) {
        mTable.put(key, value);

        return this;
    }

    public Long getLong(String key, Long defaultValue) {
        if (!mTable.containsKey(key)) {
            return defaultValue;
        }

        return (Long)mTable.get(key);
    }

    public MetaData putInteger(String key, Integer value) {
        mTable.put(key, value);

        return this;
    }

    public Integer getInt(String key, Integer defaultValue) {
        if (!mTable.containsKey(key)) {
            return defaultValue;
        }

        return (Integer)mTable.get(key);
    }

    public MetaData putBoolean(String key, Boolean value) {
        mTable.put(key, value);

        return this;
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        if (!mTable.containsKey(key)) {
            return defaultValue;
        }

        return (Boolean)mTable.get(key);
    }

    public MetaData put(String key, Object value) {
        mTable.put(key, value);

        return this;
    }

    public Object get(String key) {
        if (!mTable.containsKey(key)) {
            return null;
        }

        return mTable.get(key);
    }
}

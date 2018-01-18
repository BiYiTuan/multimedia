package com.multimedia.metadata;

import java.util.HashMap;
import java.util.Map;

public final class MetaData {
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

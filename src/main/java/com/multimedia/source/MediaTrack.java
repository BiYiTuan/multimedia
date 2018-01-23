package com.multimedia.source;

import com.multimedia.metadata.MetaData;

public interface MediaTrack {
    /**
     * get id
     */
    int getId();

    /**
     * get format
     */
    String getFormat();

    /**
     * get metadata
     */
    MetaData getMetaData();
}

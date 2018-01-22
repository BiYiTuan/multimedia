package com.multimedia.mediasource;

import com.multimedia.metadata.MetaData;

public interface MediaGroup {
    /**
     * get id
     */
    int getId();

    /**
     * get metadata
     */
    MetaData getMetaData();

    /**
     * get tracks
     */
    MediaTrack[] getTracks();
}

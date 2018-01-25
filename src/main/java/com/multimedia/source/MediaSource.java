package com.multimedia.source;

import com.multimedia.metadata.MetaData;

public interface MediaSource {
    /**
     * listener for load
     */
    interface OnLoadCompletedListener {
        /**
         * called when media has been loaded
         */
        void onLoadCompleted(MediaSource source);
    }

    /**
     * start loading
     */
    void load(OnLoadCompletedListener listener);

    /**
     * get format
     */
    String getFormat();

    /**
     * get metadata
     */
    MetaData getMetaData();

    /**
     * get groups
     */
    MediaGroup[] getGroups();

    /**
     * select group in media
     */
    void selectGroup(int groupId);

    /**
     * select track in current group
     */
    void selectTrack(int trackId);

    /**
     * release all resource
     */
    void release();

    /**
     * listener for seek
     */
    interface OnSeekCompletedListener {
        /**
         * called when source seek done
         */
        void onSeekCompleted(MediaSource source);
    }

    /**
     * seek to target time
     */
    void seek(long time, OnSeekCompletedListener listener);

    /**
     * get buffered data measured in time
     */
    long getBuffered();
}

package com.multimedia.source;

import com.multimedia.metadata.MetaData;

public interface MediaSource {
    /**
     * set data source
     */
    void setDataSource(DataSource source);

    /**
     * listener for source prepare.
     */
    interface OnPreparedListener {
        /**
         * called when source has been prepared
         */
        void onPrepared(MediaSource source);
    }

    /**
     * start to prepare
     */
    void prepare(OnPreparedListener listener);

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
     * select group in source
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
     * listener for source seek.
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

package com.multimedia.source.dash;

import com.multimedia.source.MediaGroup;
import com.multimedia.source.MediaSource;
import com.multimedia.metadata.MetaData;
import com.multimedia.source.DataSource;

public final class DashSource implements MediaSource {
    @Override
    public void setDataSource(DataSource source) {

    }

    @Override
    public void prepare(OnPreparedListener listener) {

    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public MetaData getMetaData() {
        return null;
    }

    @Override
    public MediaGroup[] getGroups() {
        return new MediaGroup[0];
    }

    @Override
    public void selectGroup(int groupId) {

    }

    @Override
    public void selectTrack(int trackId) {

    }

    @Override
    public void release() {

    }

    @Override
    public void seek(long time, OnSeekCompletedListener listener) {

    }

    @Override
    public long getBuffered() {
        return 0;
    }
}
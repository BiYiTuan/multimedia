package com.multimedia.source;

import com.multimedia.access.DataInput;
import com.multimedia.message.Handler;
import com.multimedia.message.Message;
import com.multimedia.message.MessageBus;
import com.multimedia.message.MessageType;
import com.multimedia.metadata.MetaData;

public final class ContainerSource implements MediaSource, Handler {
    private DataInput mInput;

    public ContainerSource(DataInput input) {
        mInput = input;

        MessageBus.getInstance().registerHandler(this);
    }

    @Override
    public void load(OnLoadCompletedListener listener) {
        Message msg = new Message(MessageType.MEDIA_SOURCE_LOAD, listener);

        MessageBus.getInstance().sendMessage(msg);
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

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MessageType.MEDIA_SOURCE_LOAD: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}

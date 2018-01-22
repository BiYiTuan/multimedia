package com.multimedia.foundation;

public class Message {
    public int what;
    public Object obj;

    long when;

    Message prev;
    Message next;

    public Message(int what) {
        this(what, null);
    }

    public Message(int what, Object obj) {
        this.what = what;
        this.obj = obj;
    }
}

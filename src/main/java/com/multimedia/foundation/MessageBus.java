package com.multimedia.foundation;

import java.util.ArrayList;
import java.util.List;

public final class MessageBus extends Thread {
    private static final int IDLE_TIMEOUT = 10;

    private static MessageBus sInstance = null;

    /**
     * get instance
     */
    public static MessageBus getInstance() {
        if (sInstance == null) {
            sInstance = new MessageBus();
        }

        return sInstance;
    }

    private boolean mIsRunning = false;

    private MessageQueue mMessageQueue;
    private List<MessageHandler> mHandlerList;

    private MessageBus() {
        mMessageQueue = new MessageQueue();
        mHandlerList = new ArrayList<MessageHandler>();
    }

    /**
     * register message handler
     */
    public void registerHandler(MessageHandler handler) {
        mHandlerList.add(handler);
    }

    /**
     * unregister all message handlers
     */
    public void unregisterAll() {
        mHandlerList.clear();
    }

    @Override
    public void run() {
        mIsRunning = true;

        while (true) {
            Message msg = mMessageQueue.dequeue();
            if (msg == null) {
                /**
                 * no more messages
                 */
                if (!mIsRunning) {
                    /**
                     * exit loop
                     */
                    break;
                }

                /**
                 * idle, try later
                 */
                try {
                    sleep(IDLE_TIMEOUT);
                }
                catch (InterruptedException e) {
                    //ignore
                }
            }
            else {
                long elapsedTime = msg.when - System.currentTimeMillis();

                while (elapsedTime > 0) {
                    /**
                     * not reach the time
                     */
                    try {
                        sleep(elapsedTime);
                    }
                    catch (InterruptedException e) {
                        //ignore
                        elapsedTime = msg.when - System.currentTimeMillis();
                    }
                }

                /**
                 * traverse all handlers to handle this message
                 */
                for (MessageHandler handler : mHandlerList) {
                    if (handler.handleMessage(msg)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * quit thread loop
     */
    public void quit() {
        mIsRunning = false;

        boolean isJoined = false;
        do {
            try {
                join();
                isJoined = true;
            }
            catch (InterruptedException e) {
                //ignore
            }
        }
        while (!isJoined);
    }

    /**
     * send message now
     */
    public void sendMessage(Message msg) {
        sendMessageDelayed(msg, 0);
    }

    /**
     * send message after target delay
     */
    public void sendMessageDelayed(Message msg, long delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("invalid delay");
        }

        if (!mIsRunning) {
            throw new IllegalStateException("not running");
        }

        msg.when = System.currentTimeMillis() + delay;

        mMessageQueue.enqueue(msg);
    }
}

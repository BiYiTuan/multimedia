package com.multimedia.message;

import java.util.ArrayList;
import java.util.List;

public final class MessageBus extends Thread {
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

    private List<Handler> mHandlerList;

    private MessageQueue mMessageQueue;

    private boolean mQuitLoop;

    private MessageBus() {
        mHandlerList = new ArrayList<Handler>();

        mMessageQueue = new MessageQueue();
    }

    /**
     * register message handler
     */
    public void registerHandler(Handler handler) {
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
        while (true) {
            Message msg = next();
            if (msg == null) {
                /**
                 * no more message, exit
                 */
                break;
            }

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
        }
    }

    private Message next() {
        Message msg;

        synchronized (this) {
            if (!mQuitLoop && mMessageQueue.isEmpty()) {
                /**
                 * idle, wait messages
                 */
                boolean isNotify = false;
                do {
                    try {
                        wait();
                        isNotify = true;
                    }
                    catch (InterruptedException e) {
                        //ignore
                    }
                }
                while (!isNotify);
            }
            else if (mQuitLoop && !mMessageQueue.isEmpty()) {
                /**
                 * discard messages in queue when quitting
                 */
                do {
                    mMessageQueue.dequeue();
                }
                while (!mMessageQueue.isEmpty());
            }

            msg = mMessageQueue.dequeue();
        }

        return msg;
    }

    /**
     * quit thread loop
     */
    public void quit() {
        synchronized (this) {
            mQuitLoop = true;

            /**
             * notify if waiting messages
             */
            notify();
        }

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

        msg.when = System.currentTimeMillis() + delay;

        synchronized (this) {
            if (!mQuitLoop) {
                /**
                 * can not send messages when quitting
                 */
            }
            else {
                mMessageQueue.enqueue(msg);

                /**
                 * notify if waiting messages
                 */
                notify();
            }
        }
    }
}

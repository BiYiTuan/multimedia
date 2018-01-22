package com.multimedia.foundation;

class MessageQueue {
    private Message mHead;
    private Message mTail;

    private int mCount = 0;

    public MessageQueue() {
        mHead = new Message(-1);
        mTail = new Message(-2);

        mHead.next = mTail;
        mTail.prev = mHead;
    }

    /**
     * enqueue a message
     */
    public void enqueue(Message msg) {
        synchronized (this) {
            Message curr = mHead.next;

            if (curr == mTail) {
                /**
                 * empty list, add
                 */
                mHead.next = msg;
                msg.prev = mHead;

                msg.next = mTail;
                mTail.prev = msg;
            }
            else {
                /**
                 * insert into list by time increase order
                 */
                while (curr != mTail) {
                    if (msg.when < curr.when) {
                        break;
                    }

                    curr = curr.next;
                }

                msg.prev = curr.prev;
                msg.next = curr;

                curr.prev.next = msg;
                curr.prev = msg;
            }

            mCount++;
        }
    }

    /**
     * dequeue a message
     */
    public Message dequeue() {
        Message msg;

        synchronized (this) {
            Message curr = mHead.next;

            if (curr == mTail) {
                /**
                 * empty list
                 */
                msg = null;
            }
            else {
                /**
                 * remove from head
                 */
                curr.next.prev = curr.prev;
                curr.prev.next = curr.next;

                curr.prev = null;
                curr.next = null;

                msg = curr;
                mCount--;
            }
        }

        return msg;
    }

    /**
     * is queue empty
     */
    public boolean isEmpty() {
        boolean ret;

        synchronized (this) {
            ret = mHead.next == mTail;
        }

        return ret;
    }

    /**
     * get queue length
     */
    public int size() {
        int ret;

        synchronized (this) {
            ret = mCount;
        }

        return ret;
    }
}

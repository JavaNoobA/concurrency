package com.eru.concurrency.inaction.ch8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by eru on 2020/3/15.
 */
public class PauseControl extends ReentrantLock {
    // 线程暂停标志
    private volatile boolean suspended = false;
    private final Condition condSuspended = newCondition();

    /**
     * 线程暂停
     */
    public void requestPause(){
        suspended = true;
    }

    public void proceed(){
        lock();
        try {
            suspended = false;
            condSuspended.signalAll();
        }finally {
            unlock();
        }
    }

    /**
     * 当前线程仅在线程暂挂标记不为true的情况下 才执行指定的目标动作
     * @param targetAction 目标动作
     */
    public void pauseIfNeccessary(Runnable targetAction) throws InterruptedException {
        lock();
        try {
            while (suspended){
                condSuspended.await();
            }
            targetAction.run();
        }finally {
            unlock();
        }
    }
}

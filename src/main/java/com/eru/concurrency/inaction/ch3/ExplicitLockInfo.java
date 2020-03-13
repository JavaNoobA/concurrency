package com.eru.concurrency.inaction.ch3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by eru on 2020/3/12.
 */
public class ExplicitLockInfo {
    private static final Lock lock = new ReentrantLock();
    private static int shareData = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shareData = 1;
                }finally {
                    lock.unlock();
                }

            }
        });
        t.start();
        Thread.sleep(100);
        lock.lock();
        try {
            System.out.println("shareData:" + shareData);
        }finally {
            lock.unlock();
        }
    }
}

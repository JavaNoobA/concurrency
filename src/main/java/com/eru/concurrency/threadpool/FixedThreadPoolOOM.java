package com.eru.concurrency.threadpool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示OOM的情况：设置-Xmx8m -Xms8m
 * Created by eru on 2020/3/8.
 */
public class FixedThreadPoolOOM {
    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorService service = Executors.newFixedThreadPool(5);
        new Date();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            service.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

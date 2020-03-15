package com.eru.concurrency.inaction.ch8;

import com.eru.concurrency.inaction.util.Debug;
import com.eru.concurrency.inaction.util.Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eru on 2020/3/15.
 */
public class ThreadMonitorDemo {
    volatile boolean inited = false;
    static int threadIndex = 0;
    final static Logger LOGGER = Logger.getAnonymousLogger();
    final BlockingQueue<String> channel = new ArrayBlockingQueue<String>(100);

    public static void main(String[] args) throws InterruptedException {
        ThreadMonitorDemo demo = new ThreadMonitorDemo();
        demo.init();
        for (int i = 0; i < 100; i++) {
            demo.service("test-" + i);
        }
        Thread.sleep(2000);
        System.exit(0);
    }

    private void service(String msg) throws InterruptedException {
        channel.put(msg);
    }

    public synchronized void init(){
        if (inited){
            return;
        }
        Debug.info("init...");
        WorkerThread t = new WorkerThread();
        t.setName("Worker0-" + threadIndex++);
        t.setUncaughtExceptionHandler(new ThreadMonitor());
        t.start();
        inited = true;
    }

    private class ThreadMonitor implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Debug.info("Current Thread is `t`:%s, it is still alive:%s",
                    Thread.currentThread() == t, t.isAlive());
            String threadInfo = t.getName();
            LOGGER.log(Level.SEVERE, threadInfo + " terminated:", e);

            // 创建并启动替代线程
            LOGGER.info("About to restart" + threadInfo);
            // 重置启动标志
            inited = false;
            init();
        }
    }

    class WorkerThread extends Thread{
        @Override
        public void run() {
            Debug.info("Do something important...");
            String msg;
            for (;;){
                try {
                    msg = channel.take();
                    process(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void process(String msg) {
        Debug.info(msg);
        // 模拟随机异常
        int i = (int)Math.random() * 100;
        if (i < 2){
            throw new RuntimeException("test");
        }
        Tools.randomPause(50);
    }

}

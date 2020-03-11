package com.eru.concurrency.inaction.ch1;

import java.util.Random;

/**
 * Created by eru on 2020/3/10.
 */
public class ThreadCreationCmp {

    public static void main(String[] args) {
        Thread t;
        CountingTask ct = new CountingTask();

        //  获取处理器个数
        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();

        // 直接创建线程
        // <1600, 线程安全、竞态
        for (int i = 0; i < 2 * numberOfProcessors; i++) {
            t = new Thread(ct);
            t.start();
        }

        // 以子类继承创建线程
        // 始终为100
        for (int i = 0; i < 2 * numberOfProcessors; i++) {
            t = new CountingThread();
            t.start();
        }
    }

    static class Counter{
        int count = 0;

        public void increment(){
            count++;
        }

        public int value(){
            return count;
        }
    }

    static class CountingTask implements Runnable{

        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.println("CountingTask:" + counter.value());
        }

        private void doSomething() {
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class CountingThread extends Thread{

        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.println("CountingThread:" + counter.value());
        }

        private void doSomething() {
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

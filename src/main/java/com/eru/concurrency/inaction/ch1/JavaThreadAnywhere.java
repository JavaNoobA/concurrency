package com.eru.concurrency.inaction.ch1;

/**
 * Created by eru on 2020/3/11.
 */
public class JavaThreadAnywhere {
    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();
        String currentThreadName = currentThread.getName();
        System.out.printf("当前线程是：%s\n", currentThreadName);
        Helper helper = new Helper("Java Thread Anywhere");
        helper.run();
    }
}

class Helper implements Runnable{
    private String message;

    public Helper(String message) {
        this.message = message;
    }

    public void doSomething(){
        Thread currentThread = Thread.currentThread();
        String currentThreadName = currentThread.getName();
        System.out.printf("当前线程是：%s", currentThreadName);
        System.out.println(" doSomething with " + message);
    }

    @Override
    public void run() {
        doSomething();
    }
}

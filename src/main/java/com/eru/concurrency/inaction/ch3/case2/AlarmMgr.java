package com.eru.concurrency.inaction.ch3.case2;


import com.eru.concurrency.inaction.util.Debug;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eru on 2020/3/14.
 */
public enum AlarmMgr implements Runnable {
    INSTANCE;
    private static final AtomicBoolean initializating = new AtomicBoolean(false);
    boolean initInProgress;

    AlarmMgr() {
    }

    public void init(){
        //使用AtomicBoolean的CAS操作确保工作者线程只会被创建(并启动)一次
        if (initializating.compareAndSet(false, true)){
            Debug.info("initializating....");
            new Thread(this).start();
        }
    }


    public int sendAlarm(String message) {
        int result = 0;
        // ...
        return result;
    }

    @Override
    public void run() {

    }
}

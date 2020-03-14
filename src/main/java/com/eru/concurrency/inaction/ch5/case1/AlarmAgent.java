package com.eru.concurrency.inaction.ch5.case1;

import com.eru.concurrency.inaction.util.Debug;
import com.eru.concurrency.inaction.util.Tools;

/**
 * 告警代理
 * Created by eru on 2020/3/14.
 */
public class AlarmAgent {
    // 保存该类的唯一实例
    private static final AlarmAgent INSTANCE = new AlarmAgent();
    // 是否连上告警服务器
    private boolean connectToServer = false;
    // 心跳线程, 用于检测告警代理与告警服务器的网络连接是否正常
    private final HeartbeartThread heartbeatThread = new HeartbeartThread();

    private AlarmAgent(){}

    public static AlarmAgent getInstance(){
        return INSTANCE;
    }

    public void init(){
        connectToServer();
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    private void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doConnect();
            }
        }).start();
    }

    private void doConnect() {
        // 模拟实际耗时
        Tools.randomPause(100);
        synchronized (this){
            connectToServer = true;
            // 连接已建立完毕, 通知以唤醒告警发送线程
            notify();
        }
    }

    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this){
            while (!connectToServer){
                // 使当前线程等待直到告警代理与告警服务器的连接建立完毕
                Debug.info("Alarm agent was not connected to server.");
                wait();
            }
            // 真正将消息发送到告警服务器
            doSendAlarm(message);
        }
    }

    private void doSendAlarm(String message) {
        Debug.info("Alarm sent:%s", message);
    }

    class HeartbeartThread extends Thread{
        @Override
        public void run() {
            super.run();
        }
    }
}

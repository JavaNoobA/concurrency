package com.eru.concurrency.inaction.ch5.case1;

import com.eru.concurrency.inaction.util.Tools;

/**
 * Created by eru on 2020/3/14.
 */
public class CaseRunner5_1 {
    final static AlarmAgent alarmAgent;
    static {
        alarmAgent = AlarmAgent.getInstance();
        alarmAgent.init();
    }

    public static void main(String[] args) throws InterruptedException {
        alarmAgent.sendAlarm("Database offline!");
        Tools.randomPause(5002);
        alarmAgent.sendAlarm("XXX service unreachable!");
    }
}

package com.eru.concurrency.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by eru on 2020/3/8.
 */
public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        // 延迟执行
        //for (int i = 0; i < 1000; i++) {
        //    service.schedule(new Task(), 3, TimeUnit.SECONDS);
        //}

        // 定时执行，周期性
        for (int i = 0; i < 1000; i++) {
            service.scheduleAtFixedRate(new Task(), 3, 5, TimeUnit.SECONDS);
        }
    }
}

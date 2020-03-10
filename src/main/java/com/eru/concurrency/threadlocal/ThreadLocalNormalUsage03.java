package com.eru.concurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 10个线程打印日期
 * 共用一个SimpleDateFormat会造成线程安全问题
 * Created by eru on 2020/3/8.
 */
public class ThreadLocalNormalUsage03 {

    static ExecutorService service = Executors.newFixedThreadPool(10);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage03().date(finalI);
                    System.out.println(date);
                }
            });
        }
        service.shutdown();
    }

    public String date(int seconds){
        String s = null;
        Date date = new Date(1000 * seconds);
        synchronized (ThreadLocalNormalUsage03.class){
            s = dateFormat.format(date);
        }
        return s;
    }
}

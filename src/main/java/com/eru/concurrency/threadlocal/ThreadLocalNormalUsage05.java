package com.eru.concurrency.threadlocal;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * synchronized在高并发场景下, 效率低下
 * 利用ThreadLocal, 每个线程维护一份dateFormater, 就能避免线程安全问题
 * Created by eru on 2020/3/8.
 */
public class ThreadLocalNormalUsage05 {

    static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage05().date(finalI);
                    System.out.println(date);
                }
            });
        }
        service.shutdown();
    }

    public String date(int seconds){
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = ThreadSafeFormatter.threadLocal.get();
        return dateFormat.format(date);
    }
}

class ThreadSafeFormatter{
    public static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        }
    };

    public static ThreadLocal<SimpleDateFormat> threadLocal2 =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy:MM:dd hh:mm:ss"));
}

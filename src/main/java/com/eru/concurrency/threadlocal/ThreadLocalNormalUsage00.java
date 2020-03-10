package com.eru.concurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 两个线程打印日期
 * Created by eru on 2020/3/8.
 */
public class ThreadLocalNormalUsage00 {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(10);
                System.out.println(date);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(1880);
                System.out.println(date);
            }
        }).start();
    }

    public String date(int seconds){
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        return dateFormat.format(date);
    }
}

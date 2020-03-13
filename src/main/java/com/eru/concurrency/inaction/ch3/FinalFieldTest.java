package com.eru.concurrency.inaction.ch3;

/**
 * Created by eru on 2020/3/14.
 */
public class FinalFieldTest {
    public static void main(String[] args) {
        FinalFieldExample instance = FinalFieldExample.instance;
        System.out.println(instance.x);
        instance.reader();
    }
}

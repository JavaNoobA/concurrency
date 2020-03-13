package com.eru.concurrency.inaction.ch3;

import com.eru.concurrency.inaction.util.Debug;

/**
 * Created by eru on 2020/3/14.
 */
public class ClassLazyInitDemo {
    public static void main(String[] args) {
        Debug.info(Collaborator.class.hashCode());
        Debug.info(Collaborator.number);
        Debug.info(Collaborator.flag);
    }

    static class Collaborator{
        static int number = 1;
        static boolean flag = true;
        static {
            Debug.info("Collaborator initializing....");
        }
    }
}

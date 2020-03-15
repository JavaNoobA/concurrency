package com.eru.concurrency.inaction.ch8;

import com.eru.concurrency.inaction.util.Debug;
import com.eru.concurrency.inaction.util.Tools;

import java.util.Scanner;

/**
 * Created by eru on 2020/3/15.
 */
public class ThreadPauseDemo {
    final static PauseControl pc = new PauseControl();

    public static void main(String[] args) {
        final Runnable action = new Runnable() {
            @Override
            public void run() {
                Debug.info("Master, I'm working...");
                Tools.randomPause(300);
            }
        };
        Thread slave = new Thread(){
            @Override
            public void run() {
                try {
                    for (;;){
                        pc.pauseIfNeccessary(action);
                    }
                }catch (InterruptedException e){

                }
            }
        };
        slave.setDaemon(true);
        slave.start();
        askOnBehaveOfSlave();
    }

    private static void askOnBehaveOfSlave() {
        String answer;
        int minPause = 2000;
        Scanner sc = new Scanner(System.in);
        for (;;){
            Tools.randomPause(8000, minPause);
            pc.requestPause();
            Debug.info("Master,may I take a rest now?%n");
            Debug.info("%n(1) OK,you may take a rest%n"
                    + "(2) No, Keep working!%nPress any other key to quit:%n");
            answer = sc.next();
            if ("1".equals(answer)){
                pc.requestPause();
                Debug.info("Thank you,my master!");
                minPause = 8000;
            }else if ("2".equals(answer)){
                Debug.info("Yes,my master!");
                pc.proceed();
                minPause = 2000;
            }else {
                break;
            }
        }
        Debug.info("Game Over!");
    }

}

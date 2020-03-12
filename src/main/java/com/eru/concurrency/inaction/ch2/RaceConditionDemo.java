package com.eru.concurrency.inaction.ch2;

import com.eru.concurrency.inaction.util.Tools;

/**
 * 模拟测试并发竞态
 * Created by eru on 2020/3/11.
 */
public class RaceConditionDemo {

    public static void main(String[] args) {
        int processT = args.length > 0 ? args.length: Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[processT];
        for (int i = 0; i < processT; i++) {
            threads[i] = new WorkerThread(i, 10);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    static class WorkerThread extends Thread{
        private final int requestCount;
        RequestIDGenerator requestIDGen = new RequestIDGenerator();

        public WorkerThread(int id, int requestCount) {
            super("worker-" + id);
            this.requestCount = requestCount;
        }

        @Override
        public void run() {
            int i = requestCount;

            while (i-- > 0){
                String requestId = requestIDGen.nextId();
                processRequestId(requestId);
            }
        }

        private void processRequestId(String requestId) {
            // 模拟请求处理耗时
            Tools.randomPause(50);
            System.out.printf("%s got requestID: %s %n",
                    Thread.currentThread().getName(), requestId);
        }
    }
}

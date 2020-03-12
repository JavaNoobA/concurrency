package com.eru.concurrency.inaction.ch2;

import com.eru.concurrency.inaction.util.Tools;
import com.sun.deploy.uitoolkit.ToolkitStore;

/**
 * 可见性demo
 * Created by eru on 2020/3/12.
 */
public class VisibilityDemo {
    public static void main(String[] args) throws InterruptedException {
        TimeConsumingTask task = new TimeConsumingTask();
        Thread t = new Thread(task);
        t.start();

        // 指定的时间内任务没有执行结束, 就将其取消
        Thread.sleep(10000);
        task.cancel();
    }

    static class TimeConsumingTask implements Runnable{
        private boolean toCancel = false;

        @Override
        public void run() {
            while (!toCancel){
                if (doExecute()){
                    break;
                }
            }
            if (toCancel){
                System.out.println("Task was canceled.");
            }else{
                System.out.println("Task done.");
            }
        }

        private boolean doExecute() {
            boolean isDone = false;
            System.out.println("executing...");

            // 模拟真实操作耗时
            Tools.randomPause(50);
            return isDone;
        }

        public void cancel() {
            toCancel = true;
            System.out.println(this + " canceled;");
        }
    }
}

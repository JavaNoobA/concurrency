package com.eru.concurrency.inaction.ch3.case1;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by eru on 2020/3/14.
 */
public class Indicator {

    /**
     * 保存当前类的唯一实例
     */
    private static final Indicator INSTANCE = new Indicator();

    /**
     * 记录请求总数
     */
    private final AtomicLong requestCount = new AtomicLong(0L);

    /**
     * 记录成功总数
     */
    private final AtomicLong successCount = new AtomicLong(0L);

    /**
     * 记录失败总数
     */
    private final AtomicLong failureCount = new AtomicLong(0L);

    private Indicator(){

    }

    public static Indicator getInstance(){
        return INSTANCE;
    }

    public void newRequestReceive(){
        requestCount.incrementAndGet();
    }

    public void newRequestProcessed(){
        // 使总请求数+1，这里无需枷锁
        successCount.incrementAndGet();
    }

    public void requestProcessedFailed() {
        // 使总请求数增加1。 这里无需加锁。
        failureCount.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public long getSuccessCount() {
        return successCount.get();
    }

    public long getFailureCountCount() {
        return failureCount.get();
    }

    public void reset() {
        requestCount.set(0);
        successCount.set(0);
        failureCount.set(0);
    }

    @Override
    public String toString() {
        return "Counter [requestCount=" + requestCount + ", successCount="
                + successCount + ", failureCount=" + failureCount + "]";
    }
}

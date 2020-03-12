package com.eru.concurrency.inaction.ch2;

/**
 * Created by eru on 2020/3/11.
 */
public interface CircularSeqGenerator {

    /**
     * @return 下一个序列号
     */
    short nextSequence();
}

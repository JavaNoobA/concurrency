package com.eru.concurrency.inaction.ch2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eru on 2020/3/11.
 */
public class RequestIDGenerator implements CircularSeqGenerator{

    private static final RequestIDGenerator INSTANCE = new RequestIDGenerator();
    private static final short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1;

    /**
     * 循环生成序列号
     * @return 序列号
     */
    @Override
    public short nextSequence() {
        if (sequence > SEQ_UPPER_LIMIT){
            sequence = 0;
        } else {
          sequence++;
        }
        return sequence;
    }

    /**
     * 生成新的 Request ID
     * @return
     */
    public String nextId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");
        short seq = nextSequence();

        return "0049" + timestamp + df.format(seq);
    }

    public RequestIDGenerator getInstance(){
        return INSTANCE;
    }
}

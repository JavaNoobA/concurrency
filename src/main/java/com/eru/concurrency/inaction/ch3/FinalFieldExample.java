package com.eru.concurrency.inaction.ch3;

/**
 * Created by eru on 2020/3/14.
 */
public class FinalFieldExample {
    final int x;
    int y;
    static FinalFieldExample instance;

    public FinalFieldExample() {
        x = 1;
        y = 2;
    }

    public void writer(){
        instance = new FinalFieldExample();
    }

    public void reader(){
        final FinalFieldExample theInstance = instance;
        if (theInstance != null){
            int diff = theInstance.x - theInstance.y;
            prinf(x , y, diff);
        }
    }

    private void prinf(int x, int y, int diff) {
        System.out.printf("x={0}, y={1}, diff={2}", x, y, diff);
    }

}

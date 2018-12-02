package com.lc.synchronizer;

import java.util.concurrent.CountDownLatch;

/**
 * @author lc
 * @desc
 * @date 2018-12-02 15:49:50
 **/
public class CountDownLatchDemo {
    final static CountDownLatch latch = new CountDownLatch(2);
    public static void main(String[] args) {

        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        countDownLatchDemo.play();
        countDownLatchDemo.play();

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void play(){

        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

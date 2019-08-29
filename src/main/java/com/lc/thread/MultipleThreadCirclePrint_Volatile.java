package com.lc.thread;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * 多个线程循环打印数字，使用关键字volatile
 */
public class MultipleThreadCirclePrint_Volatile {
    public static volatile int count = 0;

    public static void main(String[] args) {
        Thread a = new Thread(new PrintTask_V(0, 3));
        Thread b = new Thread(new PrintTask_V(1, 3));
        Thread c = new Thread(new PrintTask_V(2, 3));
        a.start();
        b.start();
        c.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PrintTask_V implements Runnable {
    private int num;
    private int threadNo;

    public PrintTask_V(int num, int threadNo) {
        this.num = num;
        this.threadNo = threadNo;
    }

    @Override
    public void run() {
        while (true) {
            if (MultipleThreadCirclePrint_Volatile.count % threadNo == num) {
                System.out.println("Thread name:" + Thread.currentThread().getName() + " " + MultipleThreadCirclePrint_Volatile.count);
                MultipleThreadCirclePrint_Volatile.count++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
package com.lc.thread;

import java.io.IOException;

import static java.lang.Thread.sleep;
/**
 * 多个线程循环打印数字，使用关键字volatile
 */
public class MultipleThreadCirclePrint_Synchronized {
    public static int count = 0;

    public static void main(String[] args) {
        Thread a = new Thread(new PrintTask_S(0, 3));
        Thread b = new Thread(new PrintTask_S(1, 3));
        Thread c = new Thread(new PrintTask_S(2, 3));
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

class PrintTask_S implements Runnable {
    private int num;
    private int threadNo;

    public PrintTask_S(int num, int threadNo) {
        this.num = num;
        this.threadNo = threadNo;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (this) {
                if (MultipleThreadCirclePrint_Synchronized.count % threadNo == num) {
                    System.out.println("Thread name:" + Thread.currentThread().getName() + " " + MultipleThreadCirclePrint_Synchronized.count);
                    MultipleThreadCirclePrint_Synchronized.count++;
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
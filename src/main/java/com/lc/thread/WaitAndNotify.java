package com.lc.thread;

import static java.lang.Thread.sleep;

/**
 * 本例子模拟两个线程交替执行
 *
 * @author
 */
public class WaitAndNotify {

    public static volatile boolean flagA = true;
    public static volatile boolean flagB = false;

    public static void main(String[] args) {
        WorkThread1 a = new WorkThread1();
        WorkThread2 b = new WorkThread2();
        a.start();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.start();
    }
}

/**
 * 工作线程
 */
class WorkThread1 extends Thread {
    private char c;
    public WorkThread1() {
        c = 'a';
    }

    @Override
    public void run() {
        while (true) {
            if (WaitAndNotify.flagA) {
                System.out.print(c++);
                WaitAndNotify.flagA = false;
                WaitAndNotify.flagB = true;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class WorkThread2 extends Thread {
    private int i;

    public WorkThread2() {
        i = 1;
    }

    @Override
    public void run() {
        while (true) {
            if (WaitAndNotify.flagB) {
                System.out.print(i++);
                WaitAndNotify.flagB = false;
                WaitAndNotify.flagA = true;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



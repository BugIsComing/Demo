package com.lc.weakreferencelock;

import java.util.concurrent.locks.Lock;

public class TestWeakLock {
    private static SegmentLock<String> segmentLock = new SegmentLock<>();
    private static volatile Integer it = 0;
    private static String lockKey = "147" + "@" + "3052";

    public static void main(String[] args) {

        Thread t1 = new Thread(new Task(segmentLock, it));
        Thread t2 = new Thread(new Task(segmentLock, it));
        Thread t3 = new Thread(new Task(segmentLock, it));
        try {
            t1.start();
            Thread.sleep(1000);
            t2.start();
            Thread.sleep(1000);
            t3.start();
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {
    private Integer it;
    SegmentLock<String> segmentLock;

    public Task(SegmentLock<String> segmentLock, Integer it) {
        this.segmentLock = segmentLock;
        this.it = it;
    }

    @Override
    public void run() {
        while (true) {
            Lock lock = segmentLock.get("147"+"@"+"3052");
            //不加这一句就是顺序打印数字
            //System.gc();
            lock.lock();
            try {
                System.out.println(it++);
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

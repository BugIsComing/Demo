package com.lc.synchronizer;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * A线程随机产生一个数，如果是奇数通知B线程来消费，然后消费后再通知A继续产生；如果是偶数则通知C线程消费，消费后通知A
 */
public class MultipleThreadGenerator {
    private static Lock lockA = new ReentrantLock();
    private static Lock lockB = new ReentrantLock();
    private static Lock lockC = new ReentrantLock();
    private static Condition conditionA = lockA.newCondition();
    private static Condition conditionB = lockB.newCondition();
    private static Condition conditionC = lockC.newCondition();

    private static Random random = new Random(System.nanoTime());

    private static volatile int data;
    private static volatile boolean flag = true;

    public static void main(String[] args) {

        Thread a = new Thread(() -> {
            int count = 20;
            while (count >= 0) {
                lockA.lock();
                data = random.nextInt(100);
                System.out.println("Thread A produce " + " : " + data);
                if (data % 2 == 0) {
                    lockB.lock();
                    conditionB.signal();
                    lockB.unlock();
                } else {
                    lockC.lock();
                    conditionC.signal();
                    lockC.unlock();
                }
                try {
                    conditionA.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
            }
            flag = false;
            lockB.lock();
            conditionB.signal();
            lockB.unlock();
            lockC.lock();
            conditionC.signal();
            lockC.unlock();
        });
        a.start();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread b = new Thread(() -> {
            while (flag) {
                lockB.lock();
                lockA.lock();
                System.out.println("Thread B consume: " + data);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                conditionA.signal();
                lockA.unlock();
                try {
                    conditionB.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        b.start();

        Thread c = new Thread(() -> {
            while (flag) {
                lockC.lock();
                lockA.lock();
                System.out.println("Thread C consume: " + data);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                conditionA.signal();
                lockA.unlock();

                try {
                    conditionC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        c.start();

        try {
            a.join();
            b.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

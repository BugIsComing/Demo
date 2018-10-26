package com.lc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

/**
 * @author lc
 * @desc 记录学习线程有关的demo
 * @date 2018-10-22 22:04:40
 **/
public class ThreadDemo {
    public static void main(String[] args) {
        Runnable jobRun = new JobRun();
        new Thread(jobRun).start();

        Thread jobThread = new JobThread();
        jobThread.start();
        /**
         * 这种方式方便父线程获取子线程的执行结果
         */
        JobCallable jobCallable = new JobCallable();
        FutureTask<Integer> ft = new FutureTask<>(jobCallable);
        new Thread(ft).start();
        try {
            System.out.println(ft.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 通过实现Runnable实行线程的创建
 */
class JobRun implements Runnable {
    @Override
    public void run() {
        System.out.println("This thread is created by implementing a Runnable interface");
    }
}

/**
 * 通过继承Thread类创建线程
 */
class JobThread extends Thread {
    @Override
    public void run() {
        System.out.println("This thread is created by extending a Thread class");
    }
}

/**
 * 通过实现Callable接口创建线程
 */
class JobCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100000; i++) {
            sum += i;
        }
        sleep(2000);
        return sum;
    }
}
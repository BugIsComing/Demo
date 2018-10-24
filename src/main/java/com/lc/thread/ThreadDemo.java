package com.lc.thread;

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
    }
}

/**
 * 通过实现Runnable实行线程的创建
 */
class JobRun implements Runnable{
    @Override
    public void run(){
        System.out.println("This thread is created by implementing a Runnable interface");
    }
}

/**
 * 通过继承Thread类创建线程
 */
class JobThread extends Thread{
    @Override
    public void run(){
        System.out.println("This thread is created by extending a Thread class");
    }
}
package com.lc.synchronizer;

import java.util.concurrent.*;

/**
 * 学习CyclicBarrier
 * CyclicBarrier适合多个线程之间互相等待
 * CyclicBarrier没有实现AQS，通过ReentrantLock和Condition实现
 *
 * @author lc
 * @date 2018年12月14日12:57:20
 */
public class CyclicBarrierDemo {
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static final CyclicBarrier cb = new CyclicBarrier(4, new Runnable() {
        /**
         * 这个Runnable会被最后一个执行await操作使得state==0的线程直接调用，不会新增加线程
         */
        @Override
        public void run() {
            System.out.println("寝室四兄弟一起出发去球场");
        }
    });

    private static class GoThread extends Thread {
        private final String name;

        public GoThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "开始从宿舍出发");
            try {
                Thread.sleep(1000);
                /**
                 * 当await操作使state==0时，首先新建一个generation（后代，其实就是下一个栅栏barrier），
                 * 然后会唤醒挂在condition上的其他线程
                 */
                cb.await();//拦截线程
                System.out.println(name + "从楼底下出发");
                Thread.sleep(1000);
                System.out.println(name + "到达操场");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] str = {"李明", "王强", "刘凯", "赵杰"};
        String[] str1 = {"王二", "洪光", "雷兵", "赵三"};
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new GoThread(str[i]));
        }
        try {
            while(threadPool.getActiveCount() != 0);
            System.out.println("四个人一起到达球场，现在开始打球");
            System.out.println("现在对CyclicBarrier进行复用.....");
            System.out.println("又来了一拨人，看看愿不愿意一起打：");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //进行复用：
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new GoThread(str1[i]));
        }
        try {
            //Thread.sleep(4000);
            while(threadPool.getActiveCount() != 0);
            System.out.println("四个人一起到达球场，表示愿意一起打球，现在八个人开始打球");
            //System.out.println("现在对CyclicBarrier进行复用");
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}

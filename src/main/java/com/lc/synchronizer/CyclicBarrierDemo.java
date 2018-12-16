package com.lc.synchronizer;

import java.util.concurrent.*;

/**
 * 学习CyclicBarrier
 *
 * @author lc
 * @date 2018年12月14日12:57:20
 */
public class CyclicBarrierDemo {
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static final CyclicBarrier cb = new CyclicBarrier(4, new Runnable() {
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

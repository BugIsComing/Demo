package com.lc.client;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;


/**
 * @author lc
 * 5个线程统计词频
 * 将文件均分在5个子目录，每个线程负责一个目录
 * E:\lc\1
 * E:\lc\2
 * E:\lc\3
 * E:\lc\4
 * E:\lc\5
 */
public class CountWord {
    private final static int THREAD_NUMBER = 2;
    private final static int ORDER_NUMBER = 100;
    final static CountDownLatch LATCH = new CountDownLatch(THREAD_NUMBER);
    final static Object LOCK = new Object();
    static HashMap<String, HashMap<String, Integer>> frequency = new HashMap<>();

    public static void main(String[] args) {
        /**
         * 提前预处理将多个文件分配在多个子目录下
         */
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(new CountWorker("E:\\lc\\" + i)).start();
        }
        try {
            //等待子线程处理完成
            LATCH.await();
            HashMap<String, Integer> result = getCount();
            order(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 统计词频
     *
     * @return
     */
    private static HashMap<String, Integer> getCount() {
        HashMap<String, Integer> result = new HashMap<>();
        Iterator iter = frequency.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            HashMap<String, Integer> val = (HashMap<String, Integer>) entry.getValue();
            Iterator innerIter = val.entrySet().iterator();
            String key ;
            while (innerIter.hasNext()) {
                Map.Entry innerEntry = (Map.Entry) innerIter.next();
                key = (String) innerEntry.getKey();
                int count = ((Integer)innerEntry.getValue()).intValue();
                if (result.containsKey(key)) {
                    count += result.get(key);
                }
                result.put(key, count);
            }
        }
        return result;
    }

    /**
     * 拖PriorityQueue排序，排序对象是一个OrderElement
     *
     * @param result
     */
    private static void order(HashMap<String, Integer> result) {
        PriorityQueue<OrderElement> orderResult = new PriorityQueue<>();
        Iterator iter = result.entrySet().iterator();
        String key;
        Integer value ;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            key = (String) entry.getKey();
            value = (Integer) entry.getValue();
            orderResult.offer(new OrderElement(key, value));
        }
        int index = 0;
        OrderElement temp ;
        while (index < ORDER_NUMBER && !orderResult.isEmpty()) {
            temp = orderResult.poll();
            System.out.println(temp.getWord() + " " + temp.getFrequence());
            index++;
        }
    }

}

class OrderElement implements Comparable<OrderElement> {
    private String word;
    private int frequence;

    public OrderElement(String word, int frequence) {
        this.word = word;
        this.frequence = frequence;
    }

    public String getWord() {
        return word;
    }


    public int getFrequence() {
        return frequence;
    }

    @Override
    public int compareTo(OrderElement o) {
        //降序
        if (this.getFrequence() > o.getFrequence()) {
            return -1;
        } else if (this.getFrequence() < o.getFrequence()) {
            return 1;
        } else {
            return 0;
        }
    }
}

/**
 * 这是清除注释的worker
 */
class CountWorker implements Runnable {
    private File target;

    public CountWorker(String path) {
        this.target = new File(path);
    }

    @Override
    public void run() {

        FileInputStream inputStream;
        if (target == null) {
            return;
        }
        File[] tempList = target.listFiles();
        if (tempList == null || tempList.length == 0) {
            return;
        }

        BufferedReader bufferedReader;

        for (File item : tempList) {
            String word;
            try {
                inputStream = new FileInputStream(item);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            if (bufferedReader == null) {
                continue;
            }
            try {
                HashMap<String, Integer> countHash = new HashMap<>(16);
                String fileName = item.getName();
                while ((word = bufferedReader.readLine()) != null) {
                    if ("".equals(word)) {
                        continue;
                    }
                    int count = 1;
                    if (countHash.containsKey(word)) {
                        count = countHash.get(word) + 1;
                    }
                    countHash.put(word, count);
                }
                synchronized (CountWord.LOCK) {
                    CountWord.frequency.put(fileName, countHash);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CountWord.LATCH.countDown();
            }
        }
    }
}

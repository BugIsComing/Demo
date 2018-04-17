package com.lc.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Hashtable;
import java.util.Map;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int equalToTableSizeFor(int cap) {
        if (cap >= MAXIMUM_CAPACITY) {
            return MAXIMUM_CAPACITY;
        }
        int n = cap;
        int count = 0;
        while (n > 0) {
            count++;
            n = n >> 1;
        }
        return 1 << (count);
    }

    public static void main(String[] args) {
//        for (int i=1;i<=32;i++){
//            System.out.println( tableSizeFor(i) + " " +equalToTableSizeFor(i));
//        }

        // Map<String,String> map = new HashMap<String, String>() ;
//        Map<String, String> map2 = new Hashtable<String, String>();
//        Timer tm = new Timer();
//        map2.put(null, "ad");
        //    ConcurrentHashMap<String,String> map1 = new ConcurrentHashMap<String, String>() ;
//        new Hashtable<String, String>();
         ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//         User user = (User) ac.getBean("user");
//        HashMap<String,String> result = new HashMap<>();

//        if ("0".equalsIgnoreCase("0")) {
//            System.out.println("Hello World!");
//        }
//
//        for (int i = 1; i <= 10; i++) {
//            Random rn = new Random();
//            int answer = rn.nextInt(99)+1;
//            System.out.println("Hello World!" + answer);
//
//        }

//        String url = String.format("%s/%s", "http://lc", "updatesetting");
//        System.out.println("Hello World!" + url);
//        int probability = Integer.parseInt(" 99");
//        System.out.println("Hello World!" + probability);

    }
}

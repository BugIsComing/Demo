package com.lc.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 服务提供者启动类
 *
 * @author
 * @date 2018年11月28日12:39:58
 */
public class ProviderTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext_dubbo.xml"});
        context.start();
        System.out.println(" app run ");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

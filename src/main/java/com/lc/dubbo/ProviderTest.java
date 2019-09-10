package com.lc.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务提供者启动类
 * 这种启动dubbo的方式适合本地调试，不适合线上运行
 *
 * @author
 * @date 2018年11月28日12:39:58
 */
public class ProviderTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext_dubbo.xml"});
        context.start();
        System.out.println(" app run ");
        synchronized (ProviderTest.class) {
            while (true) {
                try {
                    ProviderTest.class.wait();
                } catch (InterruptedException e) {

                }
            }
        }
    }
}

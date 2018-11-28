package com.lc.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext_dubboConsumer.xml" });
        context.start();
        IProvider demoService = (IProvider) context.getBean("demoService11"); // 获取bean

        String message = "";
        try {
            message = demoService.build("2016-10-20");
            System.out.println(" the message from server is:" + message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

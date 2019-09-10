package com.lc.springextend;

import com.lc.springextend.model.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * spring schema 扩展实例
 * 需要注意的是spring.handlers,spring.schemas文件必须在META-INF文件夹里面
 */
public class SpringExtendTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application_springextend.xml"});
        context.start();
        Person p = (Person) context.getBean("person");
        System.out.println(p.getName());
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
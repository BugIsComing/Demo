package com.lc.client;

import com.lc.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Map<String,String> map = new HashMap<String, String>() ;
        ConcurrentHashMap<String,String> map1 = new ConcurrentHashMap<String, String>() ;
        new Hashtable<String,String>();
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) ac.getBean("user");
        HashMap<String,String> result = new HashMap<>();

        System.out.println( "Hello World!" );
    }
}

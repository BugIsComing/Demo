package com.lc.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lc
 * @desc
 * @date 2018-12-17 21:04:28
 **/
public class TestAOP {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext_aop.xml");
        EmployeeManager manager = context.getBean(EmployeeManager.class);

        manager.getEmployeeById(1);
        manager.createEmployee(new EmployeeDTO());
    }
}

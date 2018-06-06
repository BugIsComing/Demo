package com.lc.proxy;

/**
 * @author ifly_lc
 * 目标对象类
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("RealSubject");
    }
}

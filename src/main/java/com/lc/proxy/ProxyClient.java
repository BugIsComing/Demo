package com.lc.proxy;

/**
 * @author ifly_lc 
 * 静态代理客户端
 */
public class ProxyClient {
    public static void main(String[]args) {
        RealSubject realSubject = new RealSubject();
        Proxy proxy = new Proxy(realSubject);
        proxy.request();
    }
}

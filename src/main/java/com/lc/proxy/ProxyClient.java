package com.lc.proxy;

public class ProxyClient {
    public static void main(String[]args) {
        RealSubject realSubject = new RealSubject();
        Proxy proxy = new Proxy(realSubject);
        proxy.request();
    }
}

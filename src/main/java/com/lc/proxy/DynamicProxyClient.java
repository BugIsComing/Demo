package com.lc.proxy;

/**
 * @author ifly_lc
 * 动态代理客户端
 */
public class DynamicProxyClient {
    public static void main(String[] args) {
        //生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RealSubject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        /**
         * 动态生成class文件，其实现的接口通过newProxyInstance的第二个参数决定
         */
        Subject proxySubject = (Subject) java.lang.reflect.Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), proxyHandler);
        proxySubject.request();
    }
}

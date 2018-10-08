package com.lc.client;

/**
 * 测试接口的多继承关系,测试包括InterfaceDemo和InterfaceDemoClient
 *
 * @author lc
 */
public abstract class InterfaceDemo implements InterfaceProvider {
    /**
     * 如果只实现接口的部分方法，必须声明为abstract
     */
    @Override
    public void func1() {

    }
}

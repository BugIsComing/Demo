package com.lc.client;

import com.lc.proxy.Subject;

/**
 * Interface已经实现过Subject接口了，第一个类不实现Subject接口，第二个实现Subject接口
 */
public abstract class InterfaceDemoClient extends InterfaceDemo{
}

class InterfaceDemoClient2 extends InterfaceDemo implements InterfaceProvider{
    @Override
    public void func2() {

    }
}
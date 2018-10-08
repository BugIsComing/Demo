package com.lc.client;

/**
 * Interface已经实现过Subject接口了，第一个类不实现Subject接口，第二个实现Subject接口
 * 通过左边Structure查看接口继承结构可以看出，两种方式最后的方法结构完成一样
 */
public class InterfaceDemoClient extends InterfaceDemo {
    @Override
    public void func2() {

    }
}

class InterfaceDemoClient2 extends InterfaceDemo implements InterfaceProvider {
    @Override
    public void func2() {

    }
}
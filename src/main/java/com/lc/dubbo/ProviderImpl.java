package com.lc.dubbo;

/**
 * 服务具体实现
 * @author
 * @date 2018年11月28日12:28:58
 */
public class ProviderImpl implements IProvider {
    @Override
    public String build(String name) throws Exception {
        System.out.println(" got a argument: " + name);
        return "message from provider: " + name;
    }
}

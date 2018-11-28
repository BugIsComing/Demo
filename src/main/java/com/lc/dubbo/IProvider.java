package com.lc.dubbo;

/**
 * dubbo服务提供者
 * @author
 * @date 2018年11月28日12:28:02
 */
public interface IProvider {
    String build(String name) throws Exception;
}

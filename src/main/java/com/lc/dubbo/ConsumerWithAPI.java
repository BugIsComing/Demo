package com.lc.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import java.io.IOException;

/**
 * 通过API创建dubbo服务消费者，无需配置文件
 *
 * @author
 * @date 2018年11月29日17:04:46
 */
public class ConsumerWithAPI {
    public static void main(String[] args) {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("ConsumerWithAPI");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://112.74.36.223:2181");
        registry.setUsername("root");
        registry.setPassword("root");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

// 引用远程服务
        // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig<IProvider> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        // 多个注册中心可以用setRegistries()
        reference.setRegistry(registry);
        reference.setInterface(IProvider.class);
        //reference.setVersion("1.0.0");

// 和本地bean一样使用xxxService
// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        IProvider xxxService = reference.get();
        try {
            System.out.println(xxxService.build("ConsumerWithAPI"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.lc.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * 通过API创建dubbo服务提供者，无需配置文件
 *
 * @author
 * @date 2018年11月29日17:04:46
 */
public class ProviderWithAPI {
    public static void main(String[] args) {
        // 服务实现
        IProvider xxxService = new ProviderImpl();

// 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxx");
        application.setOwner("lc");

// 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://112.74.36.223:2181");
        registry.setUsername("root");
        registry.setPassword("root");

// 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(200);

// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

// 服务提供者暴露服务配置
// 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        ServiceConfig<IProvider> service = new ServiceConfig<>();
        service.setApplication(application);
        // 多个注册中心可以用setRegistries()
        service.setRegistry(registry);
        // 多个协议可以用setProtocols()
        service.setProtocol(protocol);
        service.setInterface(IProvider.class);
        service.setRef(xxxService);
        //service.setVersion("1.0.0");
        // 暴露及注册服务
        service.export();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

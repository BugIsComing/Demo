package com.lc.client;

import com.iflytek.musicsearch.redisproxy.facotry.RedisServiceFacotry;
import com.iflytek.musicsearch.redisproxy.service.SuperJedisPool;
import com.iflytek.musicsearch.redisproxy.service.impl.RedisServiceImpl;

public class RedisProxyTest {
    public static  void main(String[]args){
        RedisServiceImpl redisService = (RedisServiceImpl)RedisServiceFacotry.getRedisService();
        try {
            SuperJedisPool superJedisPool = redisService.getJedisPoolByFlag("Biz");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

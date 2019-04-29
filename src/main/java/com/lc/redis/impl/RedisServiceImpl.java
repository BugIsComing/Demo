package com.lc.redis.impl;

import com.google.gson.Gson;
import com.lc.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * redis批量读和写
 *
 * @author lc
 * @date 2019年4月29日13:40:50
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void pushDataByBatch(List list, String redisKey) {
        if (list == null || list.isEmpty()) {
            LOG.info("List is empty,Not to push");
            return;
        }
        Gson gson = new Gson();
        Long sucCount = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
            threadLocal.set(0L);
            connection.openPipeline();
            for (int i = 0; i < list.size(); i++) {
                try {
                    connection.lPush(rawKeySerialization(redisKey), rawValueSerialization(gson.toJson(list.get(i))));
                    threadLocal.set(threadLocal.get() + 1);
                } catch (Exception e) {
                    LOG.warn("Impossibly encountered uncommon error while serializing prior to pushing towards Redis");
                }
            }
            connection.closePipeline();
            return threadLocal.get();
        });
        LOG.info("Enqueue {} deal succeed ", sucCount);
    }

    @Override
    public Object dumpDataByBatch(Integer maxLength, Class clazz, String redisKey) {
        List<Object> list = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (int i = 0; i < maxLength; i++) {
                connection.rPop(rawKeySerialization(redisKey));
            }
            return null;
        }, redisTemplate.getValueSerializer());
        List result = new ArrayList();
        Gson gson = new Gson();
        if (list != null && !list.isEmpty()) {
            for (Object obj : list) {
                if (obj != null) {
                    try {
                        Object tmp = clazz.newInstance();
                        tmp = gson.fromJson(obj.toString(), clazz);
                        result.add(tmp);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * Key serialization
     *
     * @param key redis key
     * @return 序列化之后的key
     */
    private byte[] rawKeySerialization(Object key) {
        Assert.notNull(key, "non null key required");
        RedisSerializer serializer = redisTemplate.getKeySerializer();
        if (serializer == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return serializer.serialize(key);
    }

    /**
     * Value serialization
     *
     * @param value redis value
     * @return 序列化之后的value
     */
    private byte[] rawValueSerialization(Object value) {
        RedisSerializer serializer = redisTemplate.getValueSerializer();
        if (serializer == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        return serializer.serialize(value);
    }

}

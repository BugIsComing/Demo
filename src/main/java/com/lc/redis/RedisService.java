package com.lc.redis;

import java.util.List;

/**
 * redis 批量写和批量读
 *
 * @author
 * @date 2019年4月29日13:20:52
 */
public interface RedisService {
    public void pushDataByBatch(List list, String redisKey);

    public Object dumpDataByBatch(final Integer maxLength, Class clazz, final String redisKey);
}
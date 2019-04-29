package com.lc;

import com.lc.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml"
})
public class RedisTest {
    @Resource
    RedisService redisService;

    @Test
    public void test() {
        List<String> data = new ArrayList<>();
        redisService.pushDataByBatch(data, "lc");
    }
}

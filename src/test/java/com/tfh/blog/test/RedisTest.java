package com.tfh.blog.test;

import com.tfh.blog.utils.redis.Redis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 16:17
 * TODO: test
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private Redis redis;

    @Test
    public void test01() {
        String s = redis.get("name");
        System.out.println(s);

    }
}

package com.tfh.blog.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 15:50
 * TODO: 使用redis
 */
@Component
public class Redis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 写入字符串键值对
     *
     * @param key         键
     * @param value       值
     * @param millisecond 过期时间(倒计时) 毫秒为单位
     */
    public void set(String key, String value, long millisecond) {
        ValueOperations<String, String> set = stringRedisTemplate.opsForValue();
        set.set(key, value, Duration.ofMillis(millisecond));
    }

    /**
     * 获取value值
     *
     * @param key 键
     * @return String
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key 并返回 删除前的值
     * @param key 键
     * @return 删除前的值
     */
    public String del(String key) {
        return stringRedisTemplate.opsForValue().getAndDelete(key);
    }
}

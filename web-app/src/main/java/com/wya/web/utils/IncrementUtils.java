package com.wya.web.utils;

import com.wya.web.constant.CacheConstant;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class IncrementUtils {

    private static RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");

    private final static long delta = 1L;

    public static Long getIncr(String key) {
        return getIncr(key, delta);
    }

    /**
     * @param key   key
     * @param delta 增长步数
     * @return
     * @Description: 获取自增长值
     */
    public static Long getIncr(String key, long delta) {
        int currentDate = DateUtils.getCurrentDay();
        String redisKey = key + currentDate;
        Long result = redisTemplate.opsForValue().increment(redisKey, delta);
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LONG, TimeUnit.SECONDS);
        return result;
    }

    /**
     * @param key   key
     * @param value 当前值
     * @Description: 初始化自增长值
     */
    public static void setIncr(String key, String value) {
        int currentDate = DateUtils.getCurrentDay();
        String redisKey = key + currentDate;
        redisTemplate.opsForValue().set(redisKey, value);
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LONG, TimeUnit.SECONDS);
    }
}
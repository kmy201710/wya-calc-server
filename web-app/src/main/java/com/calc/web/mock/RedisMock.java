package com.calc.web.mock;

import com.calc.web.constant.CacheConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/26 12:44
 * @Description: No Description
 */
public class RedisMock {

    public static void initCreate(RedisTemplate<String, String> redisTemplate, int n) {
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        String redisKey;
        String[] arr;
        if (n == 0) {
            redisKey = CacheConstant.CACHE_KEY_CALC_RANDOM_OPERATOR;
            long now = System.currentTimeMillis();
            if ((now & 3) == 0) {
                arr = new String[]{"+", "-"};
            } else {
                arr = new String[]{"+", "-", "*", "/"};
            }
        } else {
            redisKey = CacheConstant.CACHE_KEY_CALC_RANDOM_NUMS + n;
            List<String> strList = new ArrayList<>();
            int sum = 1;
            for (int i = 0; i < n; i++) {
                sum *= 10;
            }
            for (int i = 0; i < sum; i++) {
                strList.add("" + i);
            }
            arr = new String[strList.size()];
            arr = strList.toArray(arr);
        }
        setOpt.add(redisKey, arr);
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);

        List<String> lss = setOpt.randomMembers(redisKey, 1); //随机返回5个，可以重复
        Set<String> rss = setOpt.distinctRandomMembers(redisKey, 2); //随机返回5个，没有重复
        System.out.println("lss = " + lss);
        System.out.println("rss = " + rss);
    }
}

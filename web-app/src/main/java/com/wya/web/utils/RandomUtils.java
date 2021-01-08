package com.wya.web.utils;

import com.wya.web.constant.AppConstant;
import com.wya.web.constant.CacheConstant;
import com.wya.web.mock.RedisMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.List;
import java.util.Set;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/24 7:30
 * @Description: No Description
 */
public class RandomUtils {
    private static Logger logger = LoggerFactory.getLogger(RandomUtils.class);

    private static RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");

    /**
     * 初始化
     */
//    @PostConstruct
//    public void before() {
//        redisTemplate = SpringUtils.getBean("redisTemplate");
//    }

    /**
     * https://blog.csdn.net/sinat_32829963/article/details/70232076
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /**
     * https://blog.csdn.net/u010323779/article/details/52915503
     * 随机指定范围内N个数
     *
     * @param size 随机数个数
     * @return
     */
    public static List<String> randomList(int size) {
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        String redisKey = CacheConstant.CACHE_KEY_CALC_RANDOM_OPERATOR;
        List<String> result = setOpt.randomMembers(redisKey, size);
        if (EmptyUtils.isEmpty(result)) {
            RedisMock.initCreate(redisTemplate, 0);
            return randomList(size);
        }
//        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
//        logger.info("result:{}", result);
        return result; //随机返回5个，可以重复
    }

    /**
     * 随机指定范围内N个数
     *
     * @param size 随机数个数
     * @param n    指定范围最大值
     * @return
     */
    public static List<String> randomList(int size, int n) {
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        String redisKey = CacheConstant.CACHE_KEY_CALC_RANDOM_NUMS + AppConstant.MINUS_CONCAT + n;
        List<String> result = setOpt.randomMembers(redisKey, size);
        if (EmptyUtils.isEmpty(result)) {
            RedisMock.initCreate(redisTemplate, n);
            return randomList(size, n);
        }
//        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
//        logger.info("result:{}", result);
        return result; //随机返回5个，可以重复
    }

    /**
     * 随机指定范围内N个不重复的随机数
     *
     * @param size 随机数个数
     * @param n    指定范围最大值
     * @return
     */
    public static Set<String> randomSet(int size, int n) {
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        String redisKey = CacheConstant.CACHE_KEY_CALC_RANDOM_NUMS + AppConstant.MINUS_CONCAT + n;
        Set<String> result = setOpt.distinctRandomMembers(redisKey, size);
        if (EmptyUtils.isEmpty(result)) {
            RedisMock.initCreate(redisTemplate, n);
            return randomSet(size, n);
        }
//        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
//        logger.info("result:{}", result);
        return result; //随机返回5个，没有重复
    }
}
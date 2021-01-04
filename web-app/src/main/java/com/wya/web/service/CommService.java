package com.wya.web.service;

import com.wya.pub.BaseModel;
import com.wya.web.config.properties.DynamicDataSource;
import com.wya.web.constant.CacheConstant;
import com.wya.web.utils.DateUtils;
import com.wya.web.utils.EmptyUtils;
import com.wya.web.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/28 10:25
 * @Description: No Description
 */
@Component
public class CommService implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(CommService.class);

//    @Resource(name = "dynamicDataSource")
    @Resource
    private DynamicDataSource dynamicDataSource;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CalcService calcService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyApplicationRunner class will be execute when the project was started!");
//        String env = getEnv();
//        System.out.println("env = " + env);
        logger.info("===>> dynamicDataSource:{}", dynamicDataSource);
        this.setSession("");
        this.generator();
    }

    public String setSession(String loginName) {
        logger.info("===>> setSession loginName:{}", loginName);
        String token;
        if (EmptyUtils.isEmpty(loginName)) {
            return null;
        }
        token = TokenUtils.token(loginName, loginName);
        redisTemplate.opsForValue().set(CacheConstant.CACHE_KEY_USER_TOKEN_PHONE + token, loginName);
        redisTemplate.expire(CacheConstant.CACHE_KEY_USER_TOKEN_PHONE + token, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
        return token;
    }

    public void generator() {
        calcService.getNext(1, "0");
    }

    public static <T extends BaseModel> void initCreate(T t) {
        t.setShortDate(DateUtils.getCurrentDate());
        t.setCreateTime(new Date());
        t.setCreateUser("sys");
        t.setUpdateTime(new Date());
        t.setUpdateUser("sys");
        t.setVersion(0);
        t.setEnable(true);
    }

    public static <T extends BaseModel> void initUpdate(T t) {
        t.setShortDate(DateUtils.getCurrentDate());
        t.setUpdateTime(new Date());
        t.setUpdateUser("sys");
        t.setVersion(t.getVersion() + 1);
    }
}

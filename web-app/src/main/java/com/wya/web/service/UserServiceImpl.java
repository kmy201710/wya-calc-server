package com.wya.web.service;

import com.alibaba.fastjson.JSON;
import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.constant.AppConstant;
import com.wya.web.constant.CacheConstant;
import com.wya.web.mapper.UserMapper;
import com.wya.web.model.Calc;
import com.wya.web.model.User;
import com.wya.web.utils.EmptyUtils;
import com.wya.web.utils.MD5Utils;
import com.wya.web.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CalcService calcService;

    public final static String secret_key = "ab";
    public final static String password_123 = "123";

    @Override
    public BaseMapper<User> getMapper() {
        return userMapper;
    }

    @Override
    public Object sendLoginCode(String phoneNo) {
        List<Calc> list = calcService.getNext(1, "0");
        Calc calc = list.get(0);
        if (!AppConstant.NAN_STR.equals(calc.getCalculations()) && Integer.valueOf(calc.getCalculations()) > 0) {
            User user = this.getByPhoneNo(phoneNo);
            if (EmptyUtils.isEmpty(user)) {
                calc.setVersion(-1);
            }
            return JSON.toJSON(calc);
        }
        return this.sendLoginCode(phoneNo);
    }

    @Override
    public Map<String, Object> loginByCode(User entity) {
        String phoneNo = entity.getPhoneNo();
        if (EmptyUtils.isEmpty(phoneNo)) {
            return null;
        }
        List<User> list = this.getList(entity);
        User user;
        if (!EmptyUtils.isEmpty(list)) {
            user = list.get(0);
        } else {
            user = this.register(entity);
        }
        String token = TokenUtils.token(user.getName(), phoneNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", user);
        String redisKey = CacheConstant.CACHE_KEY_USER_TOKEN_PHONE + token;
        redisTemplate.opsForValue().set(redisKey, phoneNo);
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LOGIN, TimeUnit.SECONDS);
        return resultMap;
    }

    @Override
    public Map<String, Object> loginByPwd(User entity) {
        String phoneNo = entity.getPhoneNo();
        String password = entity.getPassword();
        if (EmptyUtils.isEmpty(phoneNo) || EmptyUtils.isEmpty(password)) {
            return null;
        }
        entity.setPassword(MD5Utils.MD(secret_key + password + phoneNo));
        List<User> list = this.getList(entity);
        if (!EmptyUtils.isEmpty(list)) {
            User user = list.get(0);
            String token = TokenUtils.token(user.getName(), phoneNo);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("user", user);
            String redisKey = CacheConstant.CACHE_KEY_USER_TOKEN_PHONE + token;
            redisTemplate.opsForValue().set(redisKey, phoneNo);
            redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LOGIN, TimeUnit.SECONDS);
            return resultMap;
        }
        return null;
    }

    @Override
    public Map<String, Object> modifyPassword(User entity) {
        String phoneNo = entity.getPhoneNo();
        String password = entity.getPassword();
        if (EmptyUtils.isEmpty(phoneNo) || EmptyUtils.isEmpty(password) || password_123.equals(password)) {
            return null;
        }
        User user = this.getByPhoneNo(phoneNo);
        if (!EmptyUtils.isEmpty(user)) {
            entity.setId(user.getId());
            entity.setPassword(MD5Utils.MD(secret_key + password + phoneNo));
            userMapper.modifyPassword(entity);
            String token = TokenUtils.token(user.getName(), phoneNo);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("user", user);
            String redisKey = CacheConstant.CACHE_KEY_USER_TOKEN_PHONE + token;
            redisTemplate.opsForValue().set(redisKey, phoneNo);
            redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LOGIN, TimeUnit.SECONDS);
            return resultMap;

        }
        return null;
    }

    private User createUser(User entity) {
        entity.setShopId(AppConstant.SHOP_DEFAULT);
        entity.setName("小无芽");
        entity.setAvatar("http://192.168.0.101/image/home/wya.jpg");
        entity.setSex("");
        entity.setBirthday("");
        entity.setCertNo("");
        entity.setVerified("");
        entity.setLvCalculation("");
        CommService.initCreate(entity);
        return entity;
    }

    private User register(User entity) {
        String phoneNo = entity.getPhoneNo();
        String role = entity.getRole();
        if (EmptyUtils.isEmpty(phoneNo) || EmptyUtils.isEmpty(role) || Integer.valueOf(role) < 10) {
            return null;
        }
        User user = this.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            entity.setPassword(MD5Utils.MD(secret_key + password_123 + phoneNo));
            CommService.initCreate(entity);
            this.insert(entity);
        }
        return user;
    }

    @Override
    public User getByPhoneNo(String phoneNo) {
        User entity = new User();
        entity.setPhoneNo(phoneNo);
        List<User> list = this.getList(entity);
        if (!EmptyUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
package com.wya.web.service;

import com.alibaba.fastjson.JSON;
import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.pub.enums.RoleTagEnum;
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
    public void save(User entity) {
        if (EmptyUtils.isEmpty(entity.getId())) {
            this.insert(entity);
        } else {
            this.update(entity);
        }
    }

    @Override
    public Object sendLoginCode(String phoneNo) {
        List<Calc> list = calcService.getNext(AppConstant.NEXT_SIZE, "0");
        for (Calc calc : list) {
            if (!AppConstant.NAN_STR.equals(calc.getCalculations()) && Integer.valueOf(calc.getCalculations()) > 0) {
                User user = this.getByPhoneNo(phoneNo);
                if (EmptyUtils.isEmpty(user)) {
                    calc.setVersion(-1);
                }
                return JSON.toJSON(calc);
            }
        }
        return this.sendLoginCode(phoneNo);
    }

    @Override
    public Map<String, Object> loginByCode(User entity) {
        String phoneNo = entity.getPhoneNo();
        if (EmptyUtils.isEmpty(phoneNo)) {
            return null;
        }
        User user = this.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
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
//        String md = MD5Utils.MD(secret_key + password + phoneNo);
        String md = MD5Utils.MD(secret_key + password);
        User user = this.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            return null;
        }
        if (md.equals(user.getPassword())) {
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
//            entity.setPassword(MD5Utils.MD(secret_key + password + phoneNo));
            entity.setPassword(MD5Utils.MD(secret_key + password));
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

    private User register(User entity) {
        Long shopId = entity.getShopId();
        int roleId = entity.getRoleId();
        if (EmptyUtils.isEmpty(shopId)) {
            entity.setShopId(AppConstant.SHOP_DEFAULT);
        }
        if (EmptyUtils.isEmpty(roleId) || Integer.valueOf(roleId) < 10) {
            entity.setRoleId(RoleTagEnum.NEWCOMER.tag);
        }
        String phoneNo = entity.getPhoneNo();
        User user = this.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            user = this.createUser(entity);
        }
        return user;
    }

    private User createUser(User entity) {
        entity.setName("小无芽");
//        entity.setPassword(MD5Utils.MD(secret_key + password_123 + entity.getPhoneNo()));
        entity.setPassword(MD5Utils.MD(secret_key + password_123));
        entity.setAvatar("/image/home/wya.jpg");
        CommService.initCreate(entity);
        this.save(entity);
        return entity;
    }
}
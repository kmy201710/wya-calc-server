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
import com.wya.web.model.Login;
import com.wya.web.utils.EmptyUtils;
import com.wya.web.utils.MD5Utils;
import com.wya.web.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
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
    private LoginService loginService;
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
            CommService.initCreate(entity);
            this.insert(entity);
        } else {
            CommService.initUpdate(entity);
            this.update(entity);
        }
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
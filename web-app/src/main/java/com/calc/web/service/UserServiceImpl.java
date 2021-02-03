package com.calc.web.service;

import com.calc.web.utils.EmptyUtils;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import com.calc.web.mapper.UserMapper;
import com.calc.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
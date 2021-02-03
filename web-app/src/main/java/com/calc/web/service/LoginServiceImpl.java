package com.calc.web.service;

import com.alibaba.fastjson.JSON;
import com.calc.web.constant.AppConstant;
import com.calc.web.constant.CacheConstant;
import com.calc.web.mapper.LoginMapper;
import com.calc.web.model.Calc;
import com.calc.web.model.Login;
import com.calc.web.utils.*;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import com.calc.web.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginServiceImpl extends BaseServiceImpl<Login> implements LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private LoginMapper loginMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private CalcService calcService;

    public final static String secret_key = "ab";
    public final static String password_123 = "123";

    @Override
    public BaseMapper<Login> getMapper() {
        return loginMapper;
    }

    @Override
    public void save(Login entity) {
        if (EmptyUtils.isEmpty(entity.getId())) {
            CommService.initCreate(entity);
            this.insert(entity);
        } else {
            CommService.initUpdate(entity);
            this.update(entity);
        }
    }

    @Override
    public Object sendLoginCode(String phoneNo) {
        List<Calc> list = calcService.getNext(AppConstant.NEXT_SIZE, "0");
        for (Calc calc : list) {
            logger.info("calc:{}", JSON.toJSONString(calc));
            if (!AppConstant.NAN_STR.equals(calc.getCalcText()) && Integer.valueOf(calc.getCalcText()) > 0) {
                return JSON.toJSON(calc);
            }
        }
        return sendLoginCode(phoneNo);
    }

    @Override
    public Map<String, Object> loginByCode(Login entity) {
        String phoneNo = entity.getPhoneNo();
        if (EmptyUtils.isEmpty(phoneNo)) {
            return null;
        }
        User user = userService.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            Login login = this.register(entity);
            user = new User();
            BeanUtils.copyProperties(login, user);
            user.setId(null);
            user.setAvatar("/image/home/wya.jpg");
            userService.save(user);
        }
        Map<String, Object> resultMap = new HashMap<>();
        String token = handleToken(user);
        resultMap.put("user", user);
        resultMap.put("token", token);
        return resultMap;
    }

    @Override
    public Map<String, Object> loginByPwd(Login entity) {
        String phoneNo = entity.getPhoneNo();
        String password = entity.getPassword();
        if (EmptyUtils.isEmpty(phoneNo) || EmptyUtils.isEmpty(password)) {
            return null;
        }
        User user = userService.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
//        String md = MD5Utils.MD(secret_key + password + phoneNo);
        String md = MD5Utils.MD(secret_key + password);
        Login login = this.getByPhoneNo(phoneNo);
        if (md.equals(login.getPassword())) {
            String token = handleToken(user);
            resultMap.put("user", user);
            resultMap.put("token", token);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> modifyPassword(Login entity) {
        String phoneNo = entity.getPhoneNo();
        String password = entity.getPassword();
        if (EmptyUtils.isEmpty(phoneNo) || EmptyUtils.isEmpty(password) || password_123.equals(password)) {
            return null;
        }
        User user = userService.getByPhoneNo(phoneNo);
        if (EmptyUtils.isEmpty(user)) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        Login login = this.getByPhoneNo(phoneNo);
        if (!EmptyUtils.isEmpty(login)) {
//            login.setPassword(MD5Utils.MD(secret_key + password + phoneNo));
            login.setPassword(MD5Utils.MD(secret_key + password));
            loginMapper.modifyPassword(login);
            String token = handleToken(user);
            resultMap.put("user", user);
            resultMap.put("token", token);
        }
        return resultMap;
    }

    @Override
    public Login getByPhoneNo(String phoneNo) {
        String redisKey = CacheConstant.CACHE_KEY_LOGIN_PHONE_OBJ + phoneNo;
        String val = redisTemplate.opsForValue().get(redisKey);
        if (!EmptyUtils.isEmpty(val)) {
            return JSON.parseObject(val, Login.class);
        }
        Login entity = new Login();
        entity.setPhoneNo(phoneNo);
        List<Login> list = this.getList(entity);
        if (!EmptyUtils.isEmpty(list)) {
            Login Login = list.get(0);
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(Login));
            redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
            return Login;
        }
        return null;
    }

    private Login register(Login entity) {
        String phoneNo = entity.getPhoneNo();
        if (EmptyUtils.isEmpty(phoneNo)) {
            return null;
        }
        Login login = new Login();
        BeanUtils.copyProperties(entity, login);
        Long shopId = entity.getShopId();
        Long roleId = entity.getRoleId();
        if (EmptyUtils.isEmpty(shopId) || EmptyUtils.isEmpty(roleId)) {
            login.setShopId(AppConstant.getConfig().getShopId());
            login.setRoleId(AppConstant.getConfig().getRoleId());
        }
        String name = SequenceUtils.getSequence(IncrementUtils.getIncr(CacheConstant.CACHE_KEY_LOGIN_INCREMENT));
        login.setName(secret_key + name);
        login.setPassword(MD5Utils.MD(secret_key + password_123));
        this.save(login);
        return login;
    }

    private String handleToken(User user) {
        String token = TokenUtils.token(user.getName(), user.getPhoneNo());
        String redisKey = CacheConstant.CACHE_KEY_LOGIN_TOKEN_PHONE + token;
        redisTemplate.opsForValue().set(redisKey, user.getPhoneNo());
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LOGIN, TimeUnit.SECONDS);
        return token;
    }
}
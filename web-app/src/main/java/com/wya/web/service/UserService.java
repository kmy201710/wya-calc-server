package com.wya.web.service;

import com.wya.pub.BaseService;
import com.wya.web.model.User;

import java.util.Map;

public interface UserService extends BaseService<User> {

    Object sendLoginCode(String phoneNo);

    Map<String, Object> loginByCode(User entity);

    Map<String, Object> loginByPwd(User entity);

    Map<String, Object> modifyPassword(User entity);

    User getByPhoneNo(String phoneNo);
}
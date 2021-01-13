package com.wya.web.service;

import com.wya.pub.BaseService;
import com.wya.web.model.Login;

import java.util.Map;

public interface LoginService extends BaseService<Login> {

    Object sendLoginCode(String phoneNo);

    Map<String, Object> loginByCode(Login entity);

    Map<String, Object> loginByPwd(Login entity);

    Map<String, Object> modifyPassword(Login entity);

    Login getByPhoneNo(String phoneNo);
}
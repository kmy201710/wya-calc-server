package com.calc.web.service;

import com.calc.web.model.Login;
import com.calc.pub.BaseService;

import java.util.Map;

public interface LoginService extends BaseService<Login> {

    Object sendLoginCode(String phoneNo);

    Map<String, Object> loginByCode(Login entity);

    Map<String, Object> loginByPwd(Login entity);

    Map<String, Object> modifyPassword(Login entity);

    Login getByPhoneNo(String phoneNo);
}
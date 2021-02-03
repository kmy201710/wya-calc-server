package com.calc.web.service;

import com.calc.pub.BaseService;
import com.calc.web.model.User;

public interface UserService extends BaseService<User> {

    User getByPhoneNo(String phoneNo);
}
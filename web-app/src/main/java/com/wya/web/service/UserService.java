package com.wya.web.service;

import com.wya.pub.BaseService;
import com.wya.web.model.User;

public interface UserService extends BaseService<User> {

    User getByPhoneNo(String phoneNo);
}
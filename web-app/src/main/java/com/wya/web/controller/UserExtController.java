package com.wya.web.controller;

import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.web.model.UserExt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userExt")
public class UserExtController extends BaseController<UserExt> {
    @Override
    public BaseService<UserExt> getService() {
        return null;
    }
}
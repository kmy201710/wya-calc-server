package com.calc.web.controller;

import com.calc.web.model.UserExt;
import com.calc.pub.BaseController;
import com.calc.pub.BaseService;
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
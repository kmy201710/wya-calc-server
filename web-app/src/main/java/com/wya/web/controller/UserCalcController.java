package com.wya.web.controller;

import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.web.model.UserCalc;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCalcController extends BaseController<UserCalc> {
    @Override
    public BaseService<UserCalc> getService() {
        return null;
    }
}
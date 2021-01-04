package com.wya.web.service;

import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.model.UserCalc;
import org.springframework.stereotype.Service;

@Service
public class UserCalcServiceImpl extends BaseServiceImpl<UserCalc> implements UserCalcService{
    @Override
    public BaseMapper<UserCalc> getMapper() {
        return null;
    }
}
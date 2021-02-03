package com.calc.web.service;

import com.calc.web.model.UserExt;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserExtServiceImpl extends BaseServiceImpl<UserExt> implements UserExtService {
    @Override
    public BaseMapper<UserExt> getMapper() {
        return null;
    }

    @Override
    public void save(UserExt userExt) {

    }
}
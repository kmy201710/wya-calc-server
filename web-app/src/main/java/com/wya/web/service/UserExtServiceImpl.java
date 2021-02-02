package com.wya.web.service;

import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.model.UserExt;
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
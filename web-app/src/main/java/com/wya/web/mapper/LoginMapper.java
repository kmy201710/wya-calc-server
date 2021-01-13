package com.wya.web.mapper;

import com.wya.pub.BaseMapper;
import com.wya.web.model.Login;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper extends BaseMapper<Login> {

    void modifyPassword(Login entity);
}
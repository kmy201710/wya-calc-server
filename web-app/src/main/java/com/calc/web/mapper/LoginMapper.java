package com.calc.web.mapper;

import com.calc.web.model.Login;
import com.calc.pub.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper extends BaseMapper<Login> {

    void modifyPassword(Login entity);
}
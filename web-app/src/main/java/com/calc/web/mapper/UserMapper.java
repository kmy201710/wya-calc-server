package com.calc.web.mapper;

import com.calc.pub.BaseMapper;
import com.calc.web.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    void modifyPassword(User entity);
}
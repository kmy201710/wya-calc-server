package com.wya.web.mapper;

import com.wya.pub.BaseMapper;
import com.wya.web.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    void modifyPassword(User entity);
}
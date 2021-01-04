package com.wya.web.controller;

import com.alibaba.fastjson.JSON;
import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.pub.ResponseVo;
import com.wya.web.model.User;
import com.wya.web.service.UserService;
import com.wya.web.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public BaseService getService() {
        return userService;
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public ResponseVo pageList(@RequestBody UserVo vo) {
        logger.info("===== pageList vo:{}", JSON.toJSON(vo));
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        return this.returnSuccess(this.pageList(user, vo.getPageNum(), vo.getPageSize(), false));
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseVo get(@PathVariable Long id) {
        logger.info("===== get id:{}", id);
        return this.returnSuccess(this.getService().get(id));
    }
}
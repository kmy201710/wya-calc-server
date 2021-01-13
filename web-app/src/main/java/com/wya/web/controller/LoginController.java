package com.wya.web.controller;

import com.alibaba.fastjson.JSON;
import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.pub.ResponseVo;
import com.wya.pub.enums.ResponseEnum;
import com.wya.web.mock.ManagerMock;
import com.wya.web.model.Login;
import com.wya.web.model.User;
import com.wya.web.service.CalcService;
import com.wya.web.service.CommService;
import com.wya.web.service.LoginService;
import com.wya.web.utils.EmptyUtils;
import com.wya.web.vo.LoginVo;
import com.wya.web.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//SecurityContextController
@RestController
public class LoginController extends BaseController<Login> {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private LoginService loginService;

    @Override
    public BaseService getService() {
        return null;
    }

    @RequestMapping(value = "/login/code", method = RequestMethod.GET)
    public ResponseVo sendLoginCode(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "phone") String phoneNo) {
        logger.info("===== sendLoginCode phoneNo:{}", phoneNo);
        Object result = loginService.sendLoginCode(phoneNo);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/login/modifyPasswordCode", method = RequestMethod.GET)
    public ResponseVo sendModifyPasswordCode(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "phone") String phoneNo) {
        logger.info("===== sendModifyPasswordCode phoneNo:{}", phoneNo);
        Object result = loginService.sendLoginCode(phoneNo);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/login/code", method = RequestMethod.POST)
    public ResponseVo loginByCode(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo vo) {
        logger.info("===== loginByCode vo:{}", JSON.toJSON(vo));
        Login login = new Login();
        BeanUtils.copyProperties(vo, login);
        Map loginLogin = loginService.loginByCode(login);
        if (EmptyUtils.isEmpty(loginLogin)) {
            return this.returnFail(ResponseEnum.USER_LOGIN_ERROR);// 账号不存在或密码错误
        }
        return this.returnSuccess(loginLogin);
    }

    @RequestMapping(value = "/login/password", method = RequestMethod.POST)
    public ResponseVo loginByPwd(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginVo vo) {
        logger.info("===== loginByPwd vo:{}", JSON.toJSON(vo));
        Login login = new Login();
        BeanUtils.copyProperties(vo, login);
        Map loginLogin = loginService.loginByPwd(login);
        if (EmptyUtils.isEmpty(loginLogin)) {
            return this.returnFail(ResponseEnum.USER_LOGIN_ERROR);// 账号不存在或密码错误
        }
        return this.returnSuccess(loginLogin);
    }

    @RequestMapping(value = "/login/modifyPassword", method = RequestMethod.POST)
    public ResponseVo modifyPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginVo vo) {
        logger.info("===== modifyPassword vo:{}", JSON.toJSON(vo));
        Login login = new Login();
        BeanUtils.copyProperties(vo, login);
        Map result = loginService.modifyPassword(login);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/manager/getList", method = RequestMethod.GET)
    public ResponseVo getList(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "shop", required = false) String shopId) {
        logger.info("===== getList shopId:{}", shopId);
        Map<String, Object> resultMap = ManagerMock.initCreate(redisTemplate);
        return this.returnSuccess(resultMap);
    }
}

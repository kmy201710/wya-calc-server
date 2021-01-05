package com.wya.web.controller;

import com.alibaba.fastjson.JSON;
import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.pub.ResponseVo;
import com.wya.pub.enums.ResponseEnum;
import com.wya.pub.enums.RoleTagEnum;
import com.wya.web.constant.AppConstant;
import com.wya.web.constant.CacheConstant;
import com.wya.web.model.User;
import com.wya.web.service.CalcService;
import com.wya.web.service.CommService;
import com.wya.web.service.UserService;
import com.wya.web.utils.EmptyUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//SecurityContextController
@RestController
public class LoginController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CalcService calcService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommService commService;

    @Override
    public BaseService getService() {
        return null;
    }

    @RequestMapping(value = "/login/code", method = RequestMethod.GET)
    public ResponseVo sendLoginCode(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "phone") String phoneNo) {
        logger.info("===== sendLoginCode phoneNo:{}", phoneNo);
        Object result = userService.sendLoginCode(phoneNo);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/login/modifyPasswordCode", method = RequestMethod.GET)
    public ResponseVo sendModifyPasswordCode(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "phone") String phoneNo) {
        logger.info("===== sendModifyPasswordCode phoneNo:{}", phoneNo);
        Object result = userService.sendLoginCode(phoneNo);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/login/code", method = RequestMethod.POST)
    public ResponseVo loginByCode(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo vo) {
        logger.info("===== loginByCode vo:{}", JSON.toJSON(vo));
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        Map loginUser = userService.loginByCode(user);
        if (EmptyUtils.isEmpty(loginUser)) {
            return this.returnFail(ResponseEnum.USER_LOGIN_ERROR);// 账号不存在或密码错误
        }
        return this.returnSuccess(loginUser);
    }

    @RequestMapping(value = "/login/password", method = RequestMethod.POST)
    public ResponseVo loginByPwd(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo vo) {
        logger.info("===== loginByPwd vo:{}", JSON.toJSON(vo));
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        Map loginUser = userService.loginByPwd(user);
        if (EmptyUtils.isEmpty(loginUser)) {
            return this.returnFail(ResponseEnum.USER_LOGIN_ERROR);// 账号不存在或密码错误
        }
        return this.returnSuccess(loginUser);
    }

    @RequestMapping(value = "/login/modifyPassword", method = RequestMethod.POST)
    public ResponseVo modifyPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo vo) {
        logger.info("===== modifyPassword vo:{}", JSON.toJSON(vo));
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        Map result = userService.modifyPassword(user);
        if (EmptyUtils.isEmpty(result)) {
            return this.returnFail(ResponseEnum.SYSTEM_INNER_ERROR);// 系统繁忙，请稍后重试
        }
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/manager/getList", method = RequestMethod.GET)
    public ResponseVo getList(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "shop", required = false) String shopId) {
        logger.info("===== getList shopId:{}", shopId);
        Map<String, Object> resultMap = new HashMap();
        List marqueeList = new ArrayList();
        marqueeList.add("1***ve 成功邀请 12人, 已获奖金 20元");
        marqueeList.add("2***ve 成功邀请 6人, 已获奖金 10元");
        marqueeList.add("3***ce 成功邀请 23人, 已获奖金 20元");
        marqueeList.add("4***e 成功邀请 16人, 已获奖金 20元");
        marqueeList.add("5***e 成功邀请 3人, 已获奖金 10元");
        resultMap.put("marqueeList", marqueeList);

        List swiperList = new ArrayList();
        Map<String, String> map = this.handleData("001", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg");
        Map<String, String> map1 = this.handleData("002", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/64beebd2f4e2447db0891e8b890b5a07.png_750x200_ab33275f.png");
        Map<String, String> map2 = this.handleData("003", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg");
        Map<String, String> map3 = this.handleData("004", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=211785943,3610857266&fm=26&gp=0.jpg");
//        http://blog.sina.cn/dpool/blog/s/blog_4ba91085010094dp.html
        swiperList.add(map);
        swiperList.add(map1);
        swiperList.add(map2);
        swiperList.add(map3);
        resultMap.put("swiperList", swiperList);

        List<Map> managerList = new ArrayList<>();
        Long size = redisTemplate.opsForSet().size(CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + AppConstant.N_STR);
        size += redisTemplate.opsForSet().size(CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + AppConstant.Y_STR);
        Map<String, String> params = this.handleData("/image/home/wya.jpg", "数学表达式(" + size + ")", "/info/calc");
        Map<String, String> params1 = this.handleData("/image/home/user.png", "动态答题信息(敬请期待)", "/info/userCalc");
        Map<String, String> params2 = this.handleData("/image/home/user.png", "活动信息(敬请期待)", "/info/user");
        Map<String, String> params3 = this.handleData("/image/home/user.png", "任务信息(敬请期待)", "/info/user");
        Map<String, String> params4 = this.handleData("/image/home/user.png", "商品信息(敬请期待)", "/info/user");
        Map<String, String> params5 = this.handleData("/image/home/user.png", "麻辣香锅2(2)", "/info/user");
        Map<String, String> params6 = this.handleData("/image/home/user.png", "麻辣香锅3(3)", "/info/user");
        Map<String, String> params7 = this.handleData("/image/home/user.png", "油焖大虾4(3)", "/info/user");
        Map<String, String> params8 = this.handleData("/image/home/user.png", "油焖大虾5(3)", "/info/user");
        Map<String, String> params9 = this.handleData("/image/home/user.png", "油焖大虾6(3)", "/info/user");
        managerList.add(params);
        managerList.add(params1);
        managerList.add(params2);
        managerList.add(params3);
        managerList.add(params4);
        managerList.add(params5);
        managerList.add(params6);
        managerList.add(params7);
        managerList.add(params8);
        managerList.add(params9);
        resultMap.put("managerList", managerList);
        return this.returnSuccess(resultMap);
    }

    private Map<String, String> handleData(String id, String imgUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("imgUrl", imgUrl);
        return params;
    }

    private Map<String, String> handleData(String icon, String title, String url) {
        Map<String, String> params = new HashMap<>();
        params.put("icon", icon);
        params.put("title", title);
        params.put("url", url);
        return params;
    }
}

package com.calc.web.constant;

public class CacheConstant {

    // 10分钟
    public static final long CACHE_TIME_LOGIN = 60 * 10;
    // 10秒钟
    public static final long CACHE_TIME_SHORT = 10;
    // 10天
    public static final long CACHE_TIME_LONG = 60 * 60 * 24 * 10;
    // 12小时
    public static final long CACHE_TIME_DEFAULT = 60 * 60 * 12;// 60秒=1分钟

    // 不区分环境
    public static final String CACHE_KEY_LOGIN_INCREMENT = "login:increment:";
    public static final String CACHE_KEY_CALC_INCREMENT = "calc:increment:";

    public static final String CACHE_KEY_LOGIN_TOKEN_PHONE = "login:token:phone:";
    public static final String CACHE_KEY_LOGIN_PHONE_OBJ = "login:phone:obj:";

    public static final String CACHE_KEY_CALC_RANDOM_OPERATOR = "calc:random:operator";
    public static final String CACHE_KEY_CALC_RANDOM_NUMS = "calc:random:nums:";

    public static final String CACHE_KEY_CALC_TYPE_GENERATOR = "calc:type:generator:";
    public static final String CACHE_KEY_CALC_TYPE_OBJ = "calc:type:obj:"; // setOpt 随机指定范围内

    // 区分环境

}

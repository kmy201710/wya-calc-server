package com.wya.web.constant;

public class CacheConstant {

    // 1800秒(30分钟)
    public final static long CACHE_TIME_DEFAULT = 60 * 30;// 60秒=1分钟
    // 30秒
    public final static long CACHE_TIME_SHORT = 30;
    // 3分钟
    public final static long CACHE_TIME_LOGIN = 60 * 3;
    // 3小时
    public final static long CACHE_TIME_LONG = 60 * 60 * 3;

    // 不区分环境
    public final static String CACHE_KEY_USER_TOKEN_PHONE = "user:token:phone:";
    public final static String CACHE_KEY_CALC_RANDOM_OPERATOR = "calc:random:operator";
    public final static String CACHE_KEY_CALC_RANDOM_NUMS = "calc:random:nums";
    public final static String CACHE_KEY_CALC_GENERATOR = "calc:generator:";
    public final static String CACHE_KEY_CALC_INCREMENT = "calc:increment:";
    public final static String CACHE_KEY_CALC_TYPE_OBJ = "calc:type:obj:"; // setOpt 随机指定范围内

    // 区分环境
    public final static String CACHE_KEY_USER_OBJ = ":user:obj";
}

package com.wya.web.constant;

public class CacheConstant {

    // 1小时
    public final static long CACHE_TIME_DEFAULT = 60 * 60;// 60秒=1分钟
    // 2分钟
    public final static long CACHE_TIME_LOGIN = 60 * 2;
    // 20秒钟
    public final static long CACHE_TIME_SHORT = 20;
    // 12小时
    public final static long CACHE_TIME_LONG = 60 * 60 * 12;

    // 不区分环境
    public final static String CACHE_KEY_CALC_RANDOM_OPERATOR = "calc:random:operator";
    public final static String CACHE_KEY_CALC_RANDOM_NUMS = "calc:random:nums";
    public final static String CACHE_KEY_CALC_GENERATOR = "calc:generator:";
    public final static String CACHE_KEY_CALC_INCREMENT = "calc:increment:";
    public final static String CACHE_KEY_CALC_TYPE_OBJ = "calc:type:obj:"; // setOpt 随机指定范围内

    public final static String CACHE_KEY_USER_TOKEN_PHONE = "user:token:phone:";
    public final static String CACHE_KEY_USER_PHONE_OBJ = "user:phone:obj";
    // 区分环境

}

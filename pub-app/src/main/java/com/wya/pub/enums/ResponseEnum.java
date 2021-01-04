package com.wya.pub.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SUCCESS("10000", "成功"),
    SYSTEM_INNER_ERROR("99999", "系统繁忙，请稍后重试"),

    PARAM_IS_INVALID("10001", "参数无效"),
    PARAM_IS_BLANK("10002", "参数为空"),
    PARAM_TYPE_BIND_ERROR("10003", "参数类型错误"),
    PARAM_NOT_COMPLETE("10004", "参数缺失"),
    USER_NOT_LOGGED_IN("20001", "用户未登录"),
    USER_LOGIN_ERROR("20002", "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN("20003", "账号已被禁用"),
    USER_NOT_EXIST("20004", "用户不存在"),
    USER_HAS_EXISTED("20005", "用户已存在"),
    ;

    private String code;
    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

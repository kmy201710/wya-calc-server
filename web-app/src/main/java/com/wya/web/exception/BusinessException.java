package com.wya.web.exception;

/**
 * 自定义异常
 *
 * @author huang.he
 * @version 1.0
 * @date 2020/8/18 19:42
 */
public class BusinessException extends RuntimeException {

    private String message;

    public String getMessage() {
        return message;
    }

    public BusinessException(String message) {
        super();
        this.message = message;
    }

    public static BusinessException build(String message) {
        return new BusinessException(message);
    }
}

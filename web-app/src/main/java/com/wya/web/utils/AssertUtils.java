package com.wya.web.utils;

import com.wya.web.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 异常抛出工具类  https://blog.csdn.net/jiahao1186/article/details/102832548
 *
 * @author huang.he
 * @version 1.0
 * @date Create at 2020/9/1 15:31
 */
public class AssertUtils {
    /**
     * 不可为空
     * 若参数为空，抛BusinessException
     * @param param 单个字符串
     * @param msg 异常信息
     */
    public static void notBlank(String param, String msg) {
        if (StringUtils.isBlank(param)) {
            throw BusinessException.build(msg);
        }
    }

    /**
     * 不可为空
     * 若参数为空，抛BusinessException
     * @param params 字符串数组
     * @param msg 异常信息
     */
    public static void notBlank(String[] params, String msg) {
        for (String param : params) {
            notBlank(param, msg);
        }
    }

    /**
     * 不可为null
     * 若参数为null，抛BusinessException
     * @param param 需判断obj
     * @param msg 异常信息
     */
    public static void notNull(Object param, String msg) {
        if (null == param) {
            throw BusinessException.build(msg);
        }
    }

    /**
     * 集合不可为空，抛BusinessException
     * @param list 集合
     * @param msg 异常信息
     */
    public static void notEmpty(List<?> list, String msg) {
        if (list == null || list.size() == 0) {
            throw BusinessException.build(msg);
        }
    }

    /**
     * Map不可为空，抛BusinessException
     * @param map 集合
     * @param msg 异常信息
     */
    public static void notEmpty(Map<?, ?> map, String msg) {
        if (map == null || map.isEmpty()) {
            throw BusinessException.build(msg);
        }
    }

    /**
     * 校验数据是否为空
     * @param date 入参
     * @param msg 错误提示
     * @return String为空 ? "" :  msg
     */
    public static String validNotBlank(String date, String msg) {
        return StringUtils.isBlank(date) ? msg : "";
    }

    /**
     * 校验数据是否为空
     * @param obj 入参
     * @param msg 错误提示
     * @return obj为空 ? "" :  msg
     */
    public static String validNotNull(Object obj, String msg) {
        return obj == null ? msg : "";
    }

    /**
     * 校验数据是否为空
     * @param list 入参
     * @param msg 错误提示
     * @return list为空 ? "" :  msg
     */
    public static String validNotEmpty(List<?> list, String msg) {
        return list == null || list.size() == 0 ? msg : "";
    }


    /**
     * 校验数据不能为false
     * @param date 入参
     * @param msg 错误提示
     * @return list为空 ? "" :  msg
     */
    public static String validNotTrue(boolean date, String msg) {
        return !date ? msg : "";
    }

    /**
     * 不为true抛异常
     * @param date 数据
     * @param msg 错误信息
     */
    public static void notTrue(boolean date, String msg) {
        if (!date) {
            throw BusinessException.build(msg);
        }
    }
}
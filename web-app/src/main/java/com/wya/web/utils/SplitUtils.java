package com.wya.web.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/24 7:30
 * @Description: No Description
 */
public class SplitUtils {

    /**
     * https://www.cnblogs.com/lxqiaoyixuan/p/8541530.html
     * java从字符串中提取数字
     *
     * @param content 计算表达式
     * @return
     */
    public static List<String> splitNumber(String content) {
        List<String> list = new ArrayList<String>();
//        String regex="[^0-9]";
        String regex = "[\\+\\-\\*\\/]";
        for (String ss : content.split(regex)) {
            if (ss.length() > 0) {
                list.add(ss);
            }
        }
        return list;
    }

    /**
     * https://www.cnblogs.com/lxqiaoyixuan/p/8541530.html
     * java从字符串中提取非数字
     *
     * @param content 计算表达式
     * @return
     */
    public static List<String> splitNotNumber(String content) {
        List<String> list = new ArrayList<String>();
//        String regex="[0-9]";
        String regex="([0-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])|=";
        for (String ss : content.split(regex)) {
            if (ss.length() > 0) {
                list.add(ss);
            }
        }
        return list;
    }
}
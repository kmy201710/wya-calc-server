package com.calc.web.constant;

import com.calc.web.utils.SpringUtils;
import com.calc.pub.ShopModel;
import com.calc.web.config.properties.ShopEntity;

public class AppConstant {
    public final static String SPLIT_CONCAT = ",";
    public final static String POINT_CONCAT = ".";

    // 下划线  http://www.fhdq.net/bd/16.html
    public final static String UNDERSCORE_CONCAT = "_";
    public final static String PLUS_CONCAT = "+";
    public final static String MINUS_CONCAT = "-";
    public final static String MULTIPLY_CONCAT = "*";
    public final static String DIVIDE_CONCAT = "/";
    public final static String L_CIRCLE = "(";
    public final static String R_CIRCLE = ")";

    public final static String NULL = "";

    public final static String Y_STR = "1";
    public final static String N_STR = "0";
    public final static String NAN_STR = "NaN";

    public final static Integer Y_INT = 1;
    public final static Integer N_INT = 0;
    public final static Integer NEGATIVE_INT = -1;

    public final static Integer NEXT_SIZE = 3;

    public final static long CALC_EXP_DEFAULT = 1L;
    public final static long TASK_EXP_DEFAULT = 10L;

    public static ShopEntity shopEntity = SpringUtils.getBean("shopEntity");

    public static ShopModel getConfig() {
        return shopEntity.getConfig();
    }
}

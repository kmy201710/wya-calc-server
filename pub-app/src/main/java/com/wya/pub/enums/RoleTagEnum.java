package com.wya.pub.enums;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 16:50
 * @Description: No Description
 */
public enum RoleTagEnum {
    // 角色：管理员、小学生、初中生、高中生、大学生、自主创业、自由职业、无业
    SYS("1", "sys"),
    PRIMARY("10", "小学生"),
    MIDDLE("20", "初中生"),
    HIGH("30", "高中生"),
    UNIVERSITY("40", "大学生"),
    SELFEMPLOYED("50", "自主创业"),
    FREELANCE("60", "自由职业"),
    UNEMPLOYED("99", "自由人"),
    ;

    public String tag;
    public String name;

    RoleTagEnum(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }
}

package com.wya.web.vo;

import com.wya.pub.RequestVo;
import lombok.Data;

@Data
public class UserVo extends RequestVo {

    // ID
    private Long id;

    // 商户ID
    private Long shopId;

    // 角色：管理员、客户、教师、学生
    private String role;

    // 角色：管理员、客户、教师、学生
    private String verified;

    // 用户名
    private String name;

    // 密码
    private String password;

    // 头像
    private String avatar;

    // 性别（男，女）
    private String sex;

    // 生日
    private String birthday;

    // 电话号码
    private String phoneNo;

    // 身份证号
    private String certNo;

    // 算术等级
    private String lvCalculation;
}

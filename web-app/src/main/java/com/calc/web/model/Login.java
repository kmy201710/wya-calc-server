package com.calc.web.model;

import com.calc.pub.BaseModel;
import lombok.Data;

@Data
public class Login extends BaseModel {

	// ID
	private Long id;

	// 商户ID
	private Long shopId;

	// 用户角色：管理员、小学生、中学生、大学生、自主创业、自由职业、无业
	private Long roleId;

	// 用户名称
	private String name;

	// 手机号
	private String phoneNo;

	// 密码
	private String password;
}

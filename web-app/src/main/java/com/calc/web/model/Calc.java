package com.calc.web.model;

import com.calc.pub.BaseModel;
import lombok.Data;

@Data
public class Calc extends BaseModel {

	// ID
	private Long id;

	// 商户ID
	private Long shopId;

	// 【EXP】经验值 Experience
	private Long exp;

	// 类型
	private String type;

	// 数组
	private String nums;

	// 表达式
	private String content;

	// 计算结果
	private String calcText;
}

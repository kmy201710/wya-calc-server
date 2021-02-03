package com.calc.web.vo;

import com.calc.pub.RequestVo;
import lombok.Data;

@Data
public class CalcAdvVo extends RequestVo {

	// ID
	private Long id;

	// 商户ID
	private Long shopId;

	// 用户ID
	private Long userId;

	// 用户名称
	private String userName;

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

	// 判断
	private String isTrue;
}

package com.wya.web.model;

import com.wya.pub.BaseModel;
import lombok.Data;

@Data
public class UserCalc extends BaseModel {

	// ID
	private Long id;

	// 用户ID
	private Long userId;

	// 
	private String userName;

	// 计算ID
	private Long calculationId;

	// 类型
	private String type;

	// 数组
	private String nums;

	// 表达式
	private String content;

	// 计算结果
	private String calculations;

	// 输入文字
	private String inputText;
}

package com.wya.web.model;

import com.wya.pub.BaseModel;
import lombok.Data;

@Data
public class Calc extends BaseModel {

	// ID
	private Long Id;

	// 商户ID
	private Long shopId;

	// 类型
	private String type;

	// 表达式
	private String content;

	// 数组
	private String nums;

	// 计算结果
	private String calculations;
}

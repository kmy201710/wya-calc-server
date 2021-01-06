package com.wya.web.vo;

import com.wya.pub.RequestVo;
import lombok.Data;

@Data
public class CalcAdvVo extends RequestVo {

	// ID
	private Long id;

	// 商户ID
	private Long shopId;

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

	// 提交
	private String submit_text;

	// 验证
	private String isTrue;
}

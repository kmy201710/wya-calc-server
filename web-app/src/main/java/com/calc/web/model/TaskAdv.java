package com.calc.web.model;

import com.calc.pub.BaseModel;
import lombok.Data;

@Data
public class TaskAdv extends BaseModel {

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

	// 名称
	private String name;

	// 类型
	private String type;

	// 表达式
	private String content;

	// 最大数
	private Long maxNum;

	// 最小数
	private Long minNum;

	// 开始时间
	private String startDate;

	// 结束时间
	private String endDate;

	// 任务结果
	private String taskText;

	// 判断
	private String isTrue;
}

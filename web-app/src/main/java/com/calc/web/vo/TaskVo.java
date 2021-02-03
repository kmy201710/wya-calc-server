package com.calc.web.vo;

import com.calc.pub.RequestVo;
import lombok.Data;

@Data
public class TaskVo extends RequestVo {

	// ID
	private Long id;

	// 商户ID
	private Long shopId;

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
}

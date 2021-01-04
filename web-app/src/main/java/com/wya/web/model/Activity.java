package com.wya.web.model;

import com.wya.pub.BaseModel;
import lombok.Data;

@Data
public class Activity extends BaseModel {

	// ID
	private Long activityId;

	// 商户ID
	private Long shopId;

	// 活动名称
	private String name;

	// 类型
	private String type;

	// 内容
	private String content;

	// 失效
	private String isExpired;
}

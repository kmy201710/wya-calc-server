package com.calc.web.model;

import com.calc.pub.BaseModel;
import lombok.Data;

@Data
public class UserExt extends BaseModel {

	// ID
	private Long id;

	// 用户ID
	private Long userId;

	// 用户名称
	private String userName;

	// 【EXP】经验值 Experience
	private Long exp;

	// 【LV】等级 Level
	private Long lv;

	// 【HP】生命值 Health point
	private Long hp;

	// 【MP】魔法值 Magic point
	private Long mp;
}

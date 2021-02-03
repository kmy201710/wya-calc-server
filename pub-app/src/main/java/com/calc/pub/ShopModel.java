package com.calc.pub;

import lombok.Data;

import java.util.List;

@Data
public class ShopModel {

    // 指定环境
    private String env;

    // 商户ID
    private Long shopId;

    // 角色ID
    private Long roleId;

    // 地址
    private String address;

    // 请求Url
    private String hostUrl;

    // 等级经验
    private List<Long> exps;
}

package com.wya.pub;

import lombok.Data;

@Data
public class ShopModel {

    // 指定环境
    private String env;

    // 商户ID
    private Long shopId;

    // 角色ID
    private Long roleId;

    // 手机号
    private String phoneNo;

    // 地址
    private String address;

    // 请求Url
    private String hostUrl;
}

package com.wya.pub;

import lombok.Data;

import java.util.Date;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 15:18
 * @Description: No Description
 */
@Data
public class BaseModel {

    // 状态（正常，禁用）
    private String state;

    // 备注
    private String remark;

    // 格式化时间
    private String shortDate;

    // 创建时间
    private Date createTime;

    // 创建人
    private String createUser;

    // 修改时间
    private Date updateTime;

    // 修改人
    private String updateUser;

    // 版本号
    private Integer version;

    // 是否启用
    private boolean isEnable;
}

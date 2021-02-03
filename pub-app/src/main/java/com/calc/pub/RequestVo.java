package com.calc.pub;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestVo implements Serializable {

    // 商户ID
    private Long shopId;

    private int pageNum;

    private int pageSize;
}

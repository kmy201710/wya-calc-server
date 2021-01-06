package com.wya.web.api;

import com.wya.web.config.properties.ShopEntity;
import com.wya.web.utils.HttpUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CalcApi {

    @Resource(name = "shopEntity")
    private ShopEntity basicProperties;

    public String getNext(String param) {
        return HttpUtils.sendGet(basicProperties.getConfig().getHostUrl() + "/app/calc/next", param);
    }

    public String generator(String param) {
        return HttpUtils.sendGet(basicProperties.getConfig().getHostUrl() + "/app/calc/generator", param);
    }
}
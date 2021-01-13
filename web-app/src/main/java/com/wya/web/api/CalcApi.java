package com.wya.web.api;

import com.wya.web.config.properties.ShopEntity;
import com.wya.web.constant.AppConstant;
import com.wya.web.utils.HttpUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CalcApi {

    public String get(String id) {
        return HttpUtils.sendPost(AppConstant.getConfig().getHostUrl() + "/app/calc/get/" + id, "");
    }

    public String getNext(String param) {
        return HttpUtils.sendGet(AppConstant.getConfig().getHostUrl() + "/app/calc/next", param);
    }

    public String generator(String param) {
        return HttpUtils.sendGet(AppConstant.getConfig().getHostUrl() + "/app/calc/generator", param);
    }
}
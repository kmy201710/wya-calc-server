package com.calc.web.api;

import com.calc.web.constant.AppConstant;
import com.calc.web.utils.HttpUtils;
import org.springframework.stereotype.Component;

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
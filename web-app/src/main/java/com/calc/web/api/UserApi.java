package com.calc.web.api;

import com.calc.web.constant.AppConstant;
import com.calc.web.utils.HttpUtils;
import org.springframework.stereotype.Component;

@Component
public class UserApi {

    public String get(String id) {
        return HttpUtils.sendPost(AppConstant.getConfig().getHostUrl() + "/app/user/get/" + id, "");
    }
}
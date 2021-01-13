package com.wya.web.api;

import com.wya.web.constant.AppConstant;
import com.wya.web.utils.HttpUtils;
import org.springframework.stereotype.Component;

@Component
public class UserApi {

    public String get(String id) {
        return HttpUtils.sendPost(AppConstant.getConfig().getHostUrl() + "/app/user/get/" + id, "");
    }
}
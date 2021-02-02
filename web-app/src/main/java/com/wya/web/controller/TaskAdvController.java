package com.wya.web.controller;

import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.web.model.TaskAdv;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskAdv")
public class TaskAdvController extends BaseController<TaskAdv> {
    @Override
    public BaseService<TaskAdv> getService() {
        return null;
    }
}
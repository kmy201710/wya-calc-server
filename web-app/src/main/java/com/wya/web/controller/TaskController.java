package com.wya.web.controller;

import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.web.model.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends BaseController<Task> {
    @Override
    public BaseService<Task> getService() {
        return null;
    }
}
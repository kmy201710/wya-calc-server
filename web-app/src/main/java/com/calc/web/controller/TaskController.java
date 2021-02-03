package com.calc.web.controller;

import com.calc.web.model.Task;
import com.calc.pub.BaseController;
import com.calc.pub.BaseService;
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
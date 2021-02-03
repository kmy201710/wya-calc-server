package com.calc.web.controller;

import com.calc.pub.BaseController;
import com.calc.pub.BaseService;
import com.calc.web.model.TaskAdv;
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
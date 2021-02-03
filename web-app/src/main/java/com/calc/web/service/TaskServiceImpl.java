package com.calc.web.service;

import com.calc.web.model.Task;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {
    @Override
    public BaseMapper<Task> getMapper() {
        return null;
    }

    @Override
    public void save(Task task) {

    }
}
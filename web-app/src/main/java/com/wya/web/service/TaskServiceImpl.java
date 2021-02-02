package com.wya.web.service;

import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.model.Task;
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
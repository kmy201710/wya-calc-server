package com.wya.web.service;

import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.model.TaskAdv;
import org.springframework.stereotype.Service;

@Service
public class TaskAdvServiceImpl extends BaseServiceImpl<TaskAdv> implements TaskAdvService {
    @Override
    public BaseMapper<TaskAdv> getMapper() {
        return null;
    }

    @Override
    public void save(TaskAdv taskAdv) {

    }
}
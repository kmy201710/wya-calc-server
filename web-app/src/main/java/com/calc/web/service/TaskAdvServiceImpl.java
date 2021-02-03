package com.calc.web.service;

import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import com.calc.web.model.TaskAdv;
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
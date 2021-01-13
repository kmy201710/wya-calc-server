package com.wya.web.service;

import com.wya.pub.BaseMapper;
import com.wya.pub.BaseServiceImpl;
import com.wya.web.mapper.CalcAdvMapper;
import com.wya.web.model.CalcAdv;
import com.wya.web.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CalcAdvServiceImpl extends BaseServiceImpl<CalcAdv> implements CalcAdvService {
    private static Logger logger = LoggerFactory.getLogger(CalcAdvServiceImpl.class);

    @Resource
    private CalcAdvMapper calcAdvMapper;

    @Override
    public BaseMapper<CalcAdv> getMapper() {
        return calcAdvMapper;
    }

    @Override
    public void save(CalcAdv entity) {
        if (EmptyUtils.isEmpty(entity.getId())) {
            CommService.initCreate(entity);
            this.insert(entity);
        } else {
            CommService.initUpdate(entity);
            this.update(entity);
        }
    }
}
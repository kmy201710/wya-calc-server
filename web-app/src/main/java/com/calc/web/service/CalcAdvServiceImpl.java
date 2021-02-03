package com.calc.web.service;

import com.calc.web.mapper.CalcAdvMapper;
import com.calc.web.model.CalcAdv;
import com.calc.web.utils.EmptyUtils;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
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
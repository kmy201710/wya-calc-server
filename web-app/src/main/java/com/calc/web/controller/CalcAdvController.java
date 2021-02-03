package com.calc.web.controller;

import com.alibaba.fastjson.JSON;
import com.calc.web.model.CalcAdv;
import com.calc.web.service.CalcAdvService;
import com.calc.pub.BaseController;
import com.calc.pub.BaseService;
import com.calc.pub.ResponseVo;
import com.calc.web.vo.CalcAdvVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calcAdv")
public class CalcAdvController extends BaseController<CalcAdv> {
    private static Logger logger = LoggerFactory.getLogger(CalcAdvController.class);

    @Autowired
    private CalcAdvService calcAdvService;

    @Override
    public BaseService getService() {
        return calcAdvService;
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public ResponseVo pageList(@RequestBody CalcAdvVo vo) {
        logger.info("===== pageList vo:{}", JSON.toJSON(vo));
        CalcAdv calcAdv = new CalcAdv();
        BeanUtils.copyProperties(vo, calcAdv);
        return this.returnSuccess(this.pageList(calcAdv, vo.getPageNum(), vo.getPageSize(), true));
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseVo get(@PathVariable Long id) {
        logger.info("===== get id:{}", id);
        return this.returnSuccess(this.getService().get(id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(@RequestBody CalcAdvVo vo) {
        logger.info("===== save vo:{}", JSON.toJSON(vo));
        CalcAdv calcAdv = new CalcAdv();
        BeanUtils.copyProperties(vo, calcAdv);
        this.getService().save(calcAdv);
        return this.returnSuccess();
    }

}
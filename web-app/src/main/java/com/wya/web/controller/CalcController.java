package com.wya.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wya.pub.BaseController;
import com.wya.pub.BaseService;
import com.wya.pub.ResponseVo;
import com.wya.web.constant.AppConstant;
import com.wya.web.constant.CacheConstant;
import com.wya.web.model.Calc;
import com.wya.web.service.CalcService;
import com.wya.web.vo.CalcVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/calc")
public class CalcController extends BaseController<Calc> {
    private static Logger logger = LoggerFactory.getLogger(CalcController.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CalcService calcService;

    @Override
    public BaseService getService() {
        return calcService;
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public ResponseVo pageList(@RequestBody CalcVo vo) {
        logger.info("===== pageList vo:{}", JSON.toJSON(vo));
        Calc calc = new Calc();
        BeanUtils.copyProperties(vo, calc);
//        return this.returnSuccess(this.pageList(calc, vo.getPageNum(), vo.getPageSize(), true));

        String redisKey = CacheConstant.CACHE_KEY_CALC_TYPE_OBJ;
        Long size = redisTemplate.opsForSet().size(redisKey + 0);
        size += redisTemplate.opsForSet().size(redisKey + 1);
        size += redisTemplate.opsForSet().size(redisKey + 2);
        size += redisTemplate.opsForSet().size(redisKey + 3);
        if (size == 0) {
            return this.returnFail();
        }
        PageInfo<Calc> pageInfo = this.pageList(calc, AppConstant.Y_INT, size.intValue(), true);
        pageInfo.setTotal(size);
        return this.returnSuccess(pageInfo);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseVo getId(@PathVariable Long id) {
        logger.info("===== get id:{}", id);
        return this.returnSuccess(this.getService().get(id));
    }

    @RequestMapping(value = "/next", method = RequestMethod.GET)
    public ResponseVo getNext(@RequestParam int size, @RequestParam String tag) {
        logger.info("===== getNext size:{}, tag:{}", size, tag);
        return this.returnSuccess(calcService.getNext(size, tag));
    }

    @RequestMapping(value = "/generator")
    public ResponseVo generator(@RequestParam int size, @RequestParam String tag) {
        logger.info("===== generator size:{}, tag:{}", size, tag);
        List result = calcService.generator(size, tag);
        return this.returnSuccess(result);
    }

    @RequestMapping(value = "/compute", method = RequestMethod.POST)
    public ResponseVo compute(@RequestBody Calc vo) {
        logger.info("===== compute vo:{}", JSON.toJSON(vo));
        Calc calc = new Calc();
        BeanUtils.copyProperties(vo, calc);
        if (AppConstant.N_STR.equals(vo.getType())) {
            calcService.compute(calc);
        } else {
            calcService.compute2(calc);
        }
        logger.info("本次计算结果:{}, 计算表达式:{}", calc.getCalcText(), calc.getContent());
        return this.returnSuccess(calc);
    }
}
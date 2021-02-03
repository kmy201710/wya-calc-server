package com.calc.web.service;

import com.alibaba.fastjson.JSON;
import com.calc.web.constant.AppConstant;
import com.calc.web.constant.CacheConstant;
import com.calc.web.utils.EmptyUtils;
import com.calc.web.utils.IncrementUtils;
import com.calc.web.utils.RandomUtils;
import com.calc.web.utils.SplitUtils;
import com.calc.pub.BaseMapper;
import com.calc.pub.BaseServiceImpl;
import com.calc.web.mapper.CalcMapper;
import com.calc.web.model.Calc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CalcServiceImpl extends BaseServiceImpl<Calc> implements CalcService {
    private static Logger logger = LoggerFactory.getLogger(CalcServiceImpl.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private CalcMapper calcMapper;

    private static int cacheSize = 10;

    @Override
    public BaseMapper<Calc> getMapper() {
        return calcMapper;
    }

    @Override
    public void save(Calc entity) {
        if (EmptyUtils.isEmpty(entity.getId())) {
            CommService.initCreate(entity);
            this.insert(entity);
        } else {
            CommService.initUpdate(entity);
            this.update(entity);
        }
    }

    @Override
    public List<Calc> getNext(int size, String tag) {
        logger.info("===>> getNext size:{}, tag:{}, cacheSize:{}", size, tag, cacheSize);
        List<Calc> result = new ArrayList<>();
        Long incr = IncrementUtils.getIncr(CacheConstant.CACHE_KEY_CALC_INCREMENT, size);
        String redisKey = CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + tag;
        Long size1 = redisTemplate.opsForSet().size(redisKey);
        if (size == incr || incr.compareTo(size1) > 0) {
            System.out.println("size = " + size + " = " + incr + " = " + size1);
            cacheSize += size1;
            this.generator(AppConstant.NEXT_SIZE, tag);
            IncrementUtils.setIncr(CacheConstant.CACHE_KEY_CALC_INCREMENT, AppConstant.Y_STR);
        }
        List<String> list = redisTemplate.opsForSet().randomMembers(redisKey, size);
        for (String ss : list) {
            Calc calc = JSON.parseObject(ss, Calc.class);
            result.add(calc);
        }
        return result;
    }

    /**
     * 自动生成 100条（加法、减法、乘法、除法）
     * AppConstants.LIST_SIZE = 1000
     *
     * @param tag 0:简单 1:困难
     * @return
     */
    @Override
    public List<Calc> generator(int size, String tag) {
        logger.info("===>> generator size:{}, tag:{}, cacheSize:{}", size, tag, cacheSize);
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(CacheConstant.CACHE_KEY_CALC_TYPE_GENERATOR + tag, AppConstant.Y_STR);
        if (!ifAbsent) {
            return null;
        }
        redisTemplate.expire(CacheConstant.CACHE_KEY_CALC_TYPE_GENERATOR + tag, CacheConstant.CACHE_TIME_SHORT, TimeUnit.SECONDS);
        long now = System.currentTimeMillis();
        int n = (int) (now & 3);
        int num = n == 0 ? 1 : n;
        List<Calc> list = new ArrayList<>();
        for (int i = 0; i < cacheSize; i++) {
            List tagList = RandomUtils.randomList(size);
            List numList = RandomUtils.randomList(size, num);
            StringBuffer nums = new StringBuffer();
            StringBuffer sbf = new StringBuffer();
            if (AppConstant.N_STR.equals(tag) || AppConstant.Y_STR.equals(tag)) {
                nums.append(numList.get(0));
                sbf.append(numList.get(0));
                for (int a = 1; a < size; a++) {
                    nums.append(AppConstant.SPLIT_CONCAT + numList.get(a));
                    sbf.append(tagList.get(a)).append(numList.get(a));
                }
            } else {
                List numList2 = RandomUtils.randomList(size, num == 1 ? 1 : num - 1);
                nums.append(numList.get(0)).append(AppConstant.POINT_CONCAT).append(numList2.get(0));
                sbf.append(numList.get(0)).append(AppConstant.POINT_CONCAT).append(numList2.get(0));
                for (int a = 1; a < size; a++) {
                    nums.append(AppConstant.SPLIT_CONCAT).append(numList.get(a)).append(AppConstant.POINT_CONCAT).append(numList2.get(a));
                    sbf.append(tagList.get(a)).append(numList.get(a)).append(AppConstant.POINT_CONCAT).append(numList2.get(a));
                }
            }
            System.out.println("sbf = " + sbf.toString());
            // 计算表达式
            Calc calc = new Calc();
            calc.setShopId(AppConstant.getConfig().getShopId());
            calc.setType(tag);
            calc.setNums(nums.toString());
            calc.setContent(sbf.toString());
            if (AppConstant.N_STR.equals(tag)) {
                this.compute(calc);
                calc.setExp(AppConstant.CALC_EXP_DEFAULT);
            } else {
                this.compute2(calc);
                calc.setExp(Long.valueOf(tag) + Long.valueOf(num));
            }
            calc.setContent(sbf.toString());
//            calc.setContent(sbf.toString() + " =");
            String redisKey = CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + tag;
            redisTemplate.opsForSet().add(redisKey, JSON.toJSONString(calc));
            redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
            CommService.initCreate(calc);
            list.add(calc);
        }
        // 批量插入
        this.insertList(list);
        return list;
    }

    @Override
    public void compute(Calc entity) {
        String tag = entity.getType();
        List<String> list = SplitUtils.splitNumber(tag);
        StringBuffer sbf = new StringBuffer();
        int result;
        if (EmptyUtils.isEmpty(list)) {
            String nums = entity.getNums().replace(" ", "");
            String[] params = nums.split(AppConstant.SPLIT_CONCAT);
            if (AppConstant.NAN_STR.equals(params[0])) {
                return;
            }
            sbf.append(params[0]);
            result = Integer.valueOf(params[0]);
            if (EmptyUtils.isEmpty(tag) || AppConstant.PLUS_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("+" + params[i]);
                    result += Integer.valueOf(params[i]);
                }
            } else if (AppConstant.MINUS_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("-" + params[i]);
                    result -= Integer.valueOf(params[i]);
                }
            } else if (AppConstant.MULTIPLY_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("*" + params[i]);
                    result *= Integer.valueOf(params[i]);
                }
            } else if (AppConstant.DIVIDE_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    if (AppConstant.N_STR.equals(params[i])) {
                        entity.setCalcText(AppConstant.NAN_STR);
                        return;
                    }
                    sbf.append("/" + params[i]);
                    result /= Integer.valueOf(params[i]);
                }
            }
        } else {
            String content = entity.getContent().replace(" ", "");
            List<String> tagList = SplitUtils.splitNotNumber(content);
            List<String> numList = SplitUtils.splitNumber(content);
            result = Integer.valueOf(numList.get(0));
            int size = tagList.size();
            Calc calc = new Calc();
            boolean flag = false;
            for (int i = 0; i < size; i++) {
                calc.setType(tagList.get(i));
                calc.setNums(result + AppConstant.SPLIT_CONCAT + numList.get(i + 1));
                this.compute(calc);
                if (AppConstant.NAN_STR.equals(calc.getCalcText())) {
                    flag = true;
                    break;
                } else {
                    result = Integer.valueOf(calc.getCalcText());
                }
            }
            entity.setContent(content);
//            entity.setContent(content + " =");
            if (!flag) {
//                entity.setCalculations("" + result);
                entity.setCalcText(String.format("%d", result));
            }
            return;
        }
//        redisTemplate.opsForValue().set(CacheConstants.CACHE_KEY_CALC_CONTENT + sbf.toString(), "" + result);
//        redisTemplate.expire(CacheConstants.CACHE_KEY_CALC_CONTENT + sbf.toString(), CacheConstants.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
        entity.setContent(sbf.toString());
        entity.setCalcText("" + result);
    }

    @Override
    public void compute2(Calc entity) {
        String tag = entity.getType();
        List<String> list = SplitUtils.splitNumber(tag);
        StringBuffer sbf = new StringBuffer();
        double result;
        if (EmptyUtils.isEmpty(list)) {
            String nums = entity.getNums().replace(" ", "");
            String[] params = nums.split(AppConstant.SPLIT_CONCAT);
            if (AppConstant.NAN_STR.equals(params[0])) {
                return;
            }
            sbf.append(params[0]);
            result = Double.valueOf(params[0]);
            if (EmptyUtils.isEmpty(tag) || AppConstant.PLUS_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("+" + params[i]);
                    result += Double.valueOf(params[i]);
                }
            } else if (AppConstant.MINUS_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("-" + params[i]);
                    result -= Double.valueOf(params[i]);
                }
            } else if (AppConstant.MULTIPLY_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    sbf.append("*" + params[i]);
                    result *= Double.valueOf(params[i]);
                }
            } else if (AppConstant.DIVIDE_CONCAT.equals(tag)) {
                for (int i = 1; i < params.length; i++) {
                    if (AppConstant.N_STR.equals(params[i])) {
                        entity.setCalcText(AppConstant.NAN_STR);
                        return;
                    }
                    sbf.append("/" + params[i]);
                    result /= Double.valueOf(params[i]);
                }
            }
        } else {
            String content = entity.getContent().replace(" ", "");
            List<String> tagList = SplitUtils.splitNotNumber(content);
            List<String> numList = SplitUtils.splitNumber(content);
            result = Double.valueOf(numList.get(0));
            int size = tagList.size();
            Calc calc = new Calc();
            boolean flag = false;
            for (int i = 0; i < size; i++) {
                calc.setType(tagList.get(i));
                calc.setNums(result + AppConstant.SPLIT_CONCAT + numList.get(i + 1));
                this.compute2(calc);
                if (AppConstant.NAN_STR.equals(calc.getCalcText())) {
                    flag = true;
                    break;
                } else {
                    result = Double.valueOf(calc.getCalcText());
                }
            }
            entity.setContent(content);
//            entity.setContent(content + " =");
            if (!flag) {
//                entity.setCalculations("" + result);
                entity.setCalcText(String.format("%.3f", result));
            }
            return;
        }
//        redisTemplate.opsForValue().set(CacheConstants.CACHE_KEY_CALC_CONTENT + sbf.toString(), "" + result);
//        redisTemplate.expire(CacheConstants.CACHE_KEY_CALC_CONTENT + sbf.toString(), CacheConstants.CACHE_TIME_DEFAULT, TimeUnit.SECONDS);
        entity.setContent(sbf.toString());
        entity.setCalcText(String.format("%.3f", result));
    }

    @Override
    public int getCountIsExist(Calc calc) {
        List<Calc> list = this.getList(calc);
        if (!EmptyUtils.isEmpty(list)) {
            return list.size();
        }
        return 0;
    }
}
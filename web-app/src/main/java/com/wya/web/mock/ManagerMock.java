package com.wya.web.mock;

import com.wya.web.constant.AppConstant;
import com.wya.web.constant.CacheConstant;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/26 12:44
 * @Description: No Description
 */
public class ManagerMock {

    public static Map<String, Object> initCreate(RedisTemplate<String, String> redisTemplate) {
        Map<String, Object> resultMap = new HashMap();
        List marqueeList = new ArrayList();
        marqueeList.add("1***ve 成功邀请 12人, 已获奖金 20元");
        marqueeList.add("2***ve 成功邀请 6人, 已获奖金 10元");
        marqueeList.add("3***ce 成功邀请 23人, 已获奖金 20元");
        marqueeList.add("4***e 成功邀请 16人, 已获奖金 20元");
        marqueeList.add("5***e 成功邀请 3人, 已获奖金 10元");
        resultMap.put("marqueeList", marqueeList);

        List swiperList = new ArrayList();
        Map<String, String> map = handleData("001", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg");
        Map<String, String> map1 = handleData("002", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/64beebd2f4e2447db0891e8b890b5a07.png_750x200_ab33275f.png");
        Map<String, String> map2 = handleData("003", "http://mp-piao-admincp.qunarzz.com/mp_piao_admin_mp_piao_admin/admin/20195/dc11f0bb94057224b104a2017f313e21.jpg_750x200_feee379d.jpg");
        Map<String, String> map3 = handleData("004", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=211785943,3610857266&fm=26&gp=0.jpg");
//        http://blog.sina.cn/dpool/blog/s/blog_4ba91085010094dp.html
        swiperList.add(map);
        swiperList.add(map1);
        swiperList.add(map2);
        swiperList.add(map3);
        resultMap.put("swiperList", swiperList);

        List<Map> managerList = new ArrayList<>();
        Long size = redisTemplate.opsForSet().size(CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + AppConstant.N_STR);
        size += redisTemplate.opsForSet().size(CacheConstant.CACHE_KEY_CALC_TYPE_OBJ + AppConstant.Y_STR);
        Map<String, String> params = handleData("/image/home/wya.jpg", "数学表达式(" + size + ")", "/info/calc");
        Map<String, String> params1 = handleData("/image/home/user.png", "动态信息(敬请期待)", "/info/userCalc");
        Map<String, String> params2 = handleData("/image/home/user.png", "活动信息(敬请期待)", "/info/user");
        Map<String, String> params3 = handleData("/image/home/user.png", "任务信息(敬请期待)", "/info/user");
        Map<String, String> params4 = handleData("/image/home/user.png", "商品信息(敬请期待)", "/info/user");
        Map<String, String> params5 = handleData("/image/home/user.png", "麻辣香锅2(2)", "/info/user");
        Map<String, String> params6 = handleData("/image/home/user.png", "麻辣香锅3(3)", "/info/user");
        Map<String, String> params7 = handleData("/image/home/user.png", "油焖大虾4(3)", "/info/user");
        Map<String, String> params8 = handleData("/image/home/user.png", "油焖大虾5(3)", "/info/user");
        Map<String, String> params9 = handleData("/image/home/user.png", "油焖大虾6(3)", "/info/user");
        managerList.add(params);
        managerList.add(params1);
        managerList.add(params2);
        managerList.add(params3);
        managerList.add(params4);
        managerList.add(params5);
        managerList.add(params6);
        managerList.add(params7);
        managerList.add(params8);
        managerList.add(params9);
        resultMap.put("managerList", managerList);
        return resultMap;
    }

    private static Map<String, String> handleData(String id, String imgUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("imgUrl", imgUrl);
        return params;
    }

    private static Map<String, String> handleData(String icon, String title, String url) {
        Map<String, String> params = new HashMap<>();
        params.put("icon", icon);
        params.put("title", title);
        params.put("url", url);
        return params;
    }
}

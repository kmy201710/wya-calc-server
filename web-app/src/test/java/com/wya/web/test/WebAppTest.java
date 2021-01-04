package com.wya.web.test;

import com.wya.web.utils.SplitUtils;
import com.wya.web.utils.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 15:03
 * @Description: No Description
 */
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
public class WebAppTest {
    private static Logger logger = LoggerFactory.getLogger(WebAppTest.class);

    public static void main(String[] args) {
        int i = 16 & 2;
        System.out.println("i = " + i);

        String regex = "[\\+|\\-|\\*|\\/]";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher("-");
        System.out.println("m = " + m.matches());//返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功.

        String content = "1+123-4.56";
        List<String> list = SplitUtils.splitNumber(content);
        logger.info("list:{}", list);
        List<String> list2 = SplitUtils.splitNotNumber(content);
        logger.info("list2:{}", list2);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testList2Set() {
        int n = 2;
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        Set<String> members = setOpt.members("math:randomNames");
        System.out.println("members = " + members);
        System.out.println("----------------------------");

        List<String> result = setOpt.randomMembers("math:randomNames", 5);
        System.out.println("result = " + result);
        System.out.println("----------------------------");

//        List<String> list = RandomUtils.randomList(n, n);
//        Set<String> set = RandomUtils.randomSet(n, n);
//        System.out.println("list = " + list);
//        System.out.println("set = " + set);

    }

    @Test
    public void testList2Set2() {
        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
        List<String> strList = new ArrayList<>();
//        strList.add("双方都");
//        strList.add("广告");
//        strList.add("双方都1");
//        strList.add("双方都2");
//        strList.add("双方都3");
//        strList.add("双方都4");
//        strList.add("双方都5");
//        strList.add("双方都6");
        for (int i = 0; i < 1000; i++) {
            strList.add("" + i);
        }
        Set<String> set = new HashSet<String>();
        set.addAll(strList);
        String[] arr = new String[set.size()];
        arr = set.toArray(arr);

        String redisKey = "test:random:nums-3";
        long ss = setOpt.add(redisKey, arr);
        System.out.println(ss);
        List<String> lss = setOpt.randomMembers(redisKey, 5); //随机返回5个，可以重复
        Set<String> rss = setOpt.distinctRandomMembers(redisKey, 5); //随机返回5个，没有重复
        System.out.println("lss = " + lss);
        System.out.println("rss = " + rss);
    }
}

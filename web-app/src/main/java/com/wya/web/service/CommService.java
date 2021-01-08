package com.wya.web.service;

import com.wya.pub.BaseModel;
import com.wya.web.api.CalcApi;
import com.wya.web.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/28 10:25
 * @Description: No Description
 */
@Component
public class CommService implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(CommService.class);

    @Autowired
    private CalcApi calcApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyApplicationRunner class will be execute when the project was started!");
        this.getNext();
        this.generator();
    }

    public void getNext() {
//        logger.info("===== getNext size:{}, tag:{}", size, tag);
//        System.out.println("url = " + url + "/app/calc/next?size=3&tag=1");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                calcApi.getNext("size=3&tag=0");
                System.out.println("Task is processing.");// 此处可以插入自己bai想运行的代码片段
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);//此处启动要运行的程序。
    }

    public void generator() {
//        logger.info("===== generator size:{}, tag:{}", size, tag);
//        System.out.println("url = " + url + "/app/calc/generator?size=3&tag=1");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                calcApi.generator("size=3&tag=1");
                System.out.println("Task is processing.");// 此处可以插入自己bai想运行的代码片段
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 30000);//此处启动要运行的程序。
    }

    public static <T extends BaseModel> void initCreate(T t) {
        t.setShortDate(DateUtils.getCurrentDate());
        t.setCreateTime(new Date());
        t.setCreateUser("sys");
        t.setUpdateTime(new Date());
        t.setUpdateUser("sys");
        t.setVersion(0);
        t.setEnable(true);
    }

    public static <T extends BaseModel> void initUpdate(T t) {
        t.setShortDate(DateUtils.getCurrentDate());
        t.setUpdateTime(new Date());
        t.setUpdateUser("sys");
        t.setVersion(t.getVersion() + 1);
    }
}

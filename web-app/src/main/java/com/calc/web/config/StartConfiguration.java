package com.calc.web.config;

import com.alibaba.fastjson.JSON;
import com.calc.web.config.properties.ShopEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Configuration
public class StartConfiguration implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(StartConfiguration.class);

    @Resource(name = "shopEntity")
    private ShopEntity basicProperties;

    public StartConfiguration() {
        logger.info("======StartConfiguration=====");
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("===== StartConfiguration: init-method=====：basicProperties:{}", JSON.toJSONString(basicProperties));
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("===== StartConfiguration: destroy-method=====");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("===== StartConfiguration: afterPropertiesSet=====");
    }
}
package com.wya.web.config.properties;

import com.wya.pub.ShopModel;
import com.wya.web.factory.PropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:env/${spring.profiles.active}.yml"}, factory = PropertySourceFactory.class)
@ConfigurationProperties(prefix = ShopEntity.MESSAGE_PREFIX)
@Data
public class ShopEntity {
    public final static String MESSAGE_PREFIX = "shop-entity";

    /**
     * 默认配置
     */
    private ShopModel config;
}

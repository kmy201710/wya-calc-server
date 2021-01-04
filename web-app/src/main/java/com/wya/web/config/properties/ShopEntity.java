package com.wya.web.config.properties;

import com.wya.pub.ShopModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = {"classpath:application.yml"})
@ConfigurationProperties(prefix = ShopEntity.MESSAGE_PREFIX)
@Data
public class ShopEntity {
    public final static String MESSAGE_PREFIX = "shop-entity";

    /**
     * 创建索引的文档配置
     */
    private List<ShopModel> configs;

    /**
     * @param shopId 商户ID
     * @return 配置
     */
    public ShopModel getConfigByShopId(long shopId) {
        for (ShopModel config : configs) {
            if (config.getShopId().compareTo(shopId) == 0) {
                return config;
            }
        }
        return null;
    }
}

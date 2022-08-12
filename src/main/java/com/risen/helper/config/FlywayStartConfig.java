package com.risen.helper.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/15 10:21
 */
@Component
@Data
public class FlywayStartConfig {

    @Value("${spring.flyway.locations:db/migration}")
    private String locations;
}

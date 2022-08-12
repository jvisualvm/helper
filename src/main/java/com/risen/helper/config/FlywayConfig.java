package com.risen.helper.config;

import com.risen.helper.util.LogUtil;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/15 9:53
 */
@Configuration
@AllArgsConstructor
public class FlywayConfig {

    private DataSource dataSource;
    private FlywayStartConfig flywayStartConfig;
    private SwitchConfig switchConfig;

    @PostConstruct
    public void migrate() {
        if (switchConfig.getFlywaySwitch()) {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource).locations(flywayStartConfig.getLocations())
                    .encoding("UTF-8").outOfOrder(true).load();
            try {
                LogUtil.error("flyway开始执行脚本，迁移数据");
                flyway.migrate();
            } catch (FlywayException e) {
                LogUtil.error("flyway开始修复");
                flyway.repair();
                LogUtil.error("Flyway配置加载出错", e);
            }
        }
    }
}

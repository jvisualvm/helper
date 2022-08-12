package com.risen.helper.log.clean;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.risen.helper.config.SwitchConfig;
import com.risen.helper.log.source.LogSource;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/15 18:11
 */
@Component
@AllArgsConstructor
public class LogScheduledCleaner {

    private SwitchConfig switchConfig;
    private LogSource logSource;

    @Scheduled(cron = "0 00 00 L * ?")
    public void logScheduledCleaner() {
        if (switchConfig.getCleanSwitch()) {
            QueryWrapper wrapper = new QueryWrapper();
            logSource.getBaseMapper().delete(wrapper);
        }
    }

}

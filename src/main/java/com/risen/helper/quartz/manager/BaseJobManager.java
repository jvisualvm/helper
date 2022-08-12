package com.risen.helper.quartz.manager;

import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/2 11:12
 */
@Component
@AllArgsConstructor
public class BaseJobManager {

    private QuartzManager quartzManager;

    public void startJob(String taskKey, String cron, Class<? extends Job> cls) {
        quartzManager.addCronJob(taskKey, cls, cron, null);
    }

    public void removeJob(String jobName) {
        quartzManager.removeJob(jobName);
    }

}
